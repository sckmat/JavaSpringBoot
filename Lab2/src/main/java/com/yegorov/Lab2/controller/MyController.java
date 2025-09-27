package com.yegorov.Lab2.controller;

import com.yegorov.Lab2.exception.UnsupportedCodeException;
import com.yegorov.Lab2.exception.ValidationFailedException;
import com.yegorov.Lab2.model.Request;
import com.yegorov.Lab2.model.Response;
import com.yegorov.Lab2.service.UnsupportedCodeService;
import com.yegorov.Lab2.service.ValidationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(request.getSystemTime())
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            validationService.isValid(bindingResult);
            unsupportedCodeService.validate(request);
        } catch (ValidationFailedException e) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Validation failed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e){
            response.setCode("failed");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage("UnsupportedCodeException");
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("UnknownException");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
