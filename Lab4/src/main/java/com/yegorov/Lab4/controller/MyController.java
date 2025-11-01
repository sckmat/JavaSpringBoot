package com.yegorov.Lab4.controller;

import com.yegorov.Lab4.exception.UnsupportedCodeException;
import com.yegorov.Lab4.exception.ValidationFailedException;
import com.yegorov.Lab4.model.*;
import com.yegorov.Lab4.service.UnsupportedCodeService;
import com.yegorov.Lab4.service.ValidationService;
import com.yegorov.Lab4.util.DateTimeUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;


@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final UnsupportedCodeService unsupportedCodeService;

    public MyController(ValidationService validationService, UnsupportedCodeService unsupportedCodeService) {
        this.validationService = validationService;
        this.unsupportedCodeService = unsupportedCodeService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("request: {}", request);
        Instant now = Instant.now();
        Instant start = safeParseInstant(request.getSystemTime());

        if (start != null) {
            long ms = Duration.between(start, now).toMillis();
            double sec = ms / 1000.0;
            log.info("S2 transit time: {} ms ({} s) | start={}, now={}", ms, String.format("%.3f", sec), start, now);
        } else {
            log.warn("S2 transit time: cannot compute — missing/invalid timestamp (systemTime='{}')",
                    request.getSystemTime());
        }

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("Initial response: {}", response);

        try {
            if (bindingResult.hasErrors()) {
                bindingResult.getAllErrors().forEach(err -> log.error("Binding error: {}", err));
            }

            validationService.isValid(bindingResult);
            unsupportedCodeService.validate(request);

            log.info("Validated request successfully");
        } catch (ValidationFailedException e) {
            log.error("Validation failed: {}", e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("Response after validation error: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e){
            log.error("Unsupported code: {}", e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("Response after unsupported code: {}", response);
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Response after unexpected error: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Final response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Instant safeParseInstant(String iso) {
        if (iso == null || iso.isBlank()) return null;
        try {
            return Instant.parse(iso);
        } catch (Exception ignored) {
            return null;
        }
    }
}
