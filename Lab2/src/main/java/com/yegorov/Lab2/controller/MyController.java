package com.yegorov.Lab2.controller;

import com.yegorov.Lab2.exception.UnsupportedCodeException;
import com.yegorov.Lab2.exception.ValidationFailedException;
import com.yegorov.Lab2.model.*;
import com.yegorov.Lab2.service.*;
import com.yegorov.Lab2.util.DateTimeUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final UnsupportedCodeService unsupportedCodeService;
    private final ModifyRequestService modifyRequestService;
    private final ModifyResponseService modifyResponseService;
    private final AnnualBonusService annualBonusService;
    private final QuarterBonusService quarterBonusService;

    public MyController(
            ValidationService validationService,
            UnsupportedCodeService unsupportedCodeService,
            ModifyRequestService modifyRequestService,
            @Qualifier("modifySystemTimeResponseService") ModifyResponseService modifyResponseService,
            AnnualBonusService annualBonusService,
            QuarterBonusService quarterBonusService
    ) {
        this.validationService = validationService;
        this.unsupportedCodeService = unsupportedCodeService;
        this.modifyRequestService = modifyRequestService;
        this.modifyResponseService = modifyResponseService;
        this.annualBonusService = annualBonusService;
        this.quarterBonusService = quarterBonusService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("request: {}", request);
        setReceiveTime(request);

        Response response = buildBaseResponse(request);
        log.info("Initial response: {}", response);

        try {
            logBindingErrors(bindingResult);
            validationService.isValid(bindingResult);
            unsupportedCodeService.validate(request);
            log.info("Validated request successfully");

            calculateBonuses(request, response);

        } catch (ValidationFailedException e) {
            return handleError(response, Codes.FAILED, ErrorCodes.VALIDATION, ErrorMessages.VALIDATION,
                    HttpStatus.BAD_REQUEST, "Validation failed", e);

        } catch (UnsupportedCodeException e) {
            return handleError(response, Codes.FAILED, ErrorCodes.UNSUPPORTED, ErrorMessages.UNSUPPORTED,
                    HttpStatus.UNPROCESSABLE_ENTITY, "Unsupported code", e);

        } catch (Exception e) {
            return handleError(response, Codes.FAILED, ErrorCodes.UNKNOWN, ErrorMessages.UNKNOWN,
                    HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", e);
        }

        modifyResponseService.modify(response);
        modifyRequestService.modify(request);
        log.info("Final response: {}", response);

        return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
    }

    private void setReceiveTime(Request request) {
        String receivedAtS1 = Instant.now().toString();
        request.setSystemTime(receivedAtS1);
        log.info("S1 received request at {}", receivedAtS1);
    }

    private Response buildBaseResponse(Request request) {
        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
    }

    private void calculateBonuses(Request req, Response res) {
        Double annualBonus = annualBonusService.calculate(
                req.getPosition(), req.getSalary(), req.getBonus(), req.getWorkDays());
        log.info("Annual bonus calculated: {}", annualBonus);
        res.setAnnualBonus(annualBonus);

        Double quarterBonus = quarterBonusService.calculateQuarterBonus(
                req.getSalary(), req.getBonus(), req.getWorkDays(), req.getPosition());
        log.info("Quarter bonus calculated: {}", quarterBonus);
        res.setQuarterBonus(quarterBonus);
    }

    private void logBindingErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(err -> log.error("Binding error: {}", err));
        }
    }

    private ResponseEntity<Response> handleError(Response response,
                                                 Codes code,
                                                 ErrorCodes errorCode,
                                                 ErrorMessages errorMessage,
                                                 HttpStatus status,
                                                 String logPrefix,
                                                 Exception e) {
        log.error("{}: {}", logPrefix, e.getMessage(), e);
        response.setCode(code);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        log.info("Response after {}: {}", logPrefix.toLowerCase(), response);
        return new ResponseEntity<>(response, status);
    }
}
