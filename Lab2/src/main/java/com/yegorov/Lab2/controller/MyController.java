package com.yegorov.Lab2.controller;

import com.yegorov.Lab2.exception.UnsupportedCodeException;
import com.yegorov.Lab2.exception.ValidationFailedException;
import com.yegorov.Lab2.model.*;
import com.yegorov.Lab2.service.UnsupportedCodeService;
import com.yegorov.Lab2.service.ValidationService;
import com.yegorov.Lab2.util.DateTimeUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
