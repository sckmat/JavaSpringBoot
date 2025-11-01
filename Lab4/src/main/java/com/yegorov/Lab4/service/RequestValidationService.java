package com.yegorov.Lab4.service;

import com.yegorov.Lab4.exception.ValidationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Slf4j
@Service
public class RequestValidationService implements ValidationService {

    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            log.error("ValidationFailedException");
            throw new ValidationFailedException(Objects.requireNonNull(bindingResult.getFieldError()).toString());
        }
    }
}
