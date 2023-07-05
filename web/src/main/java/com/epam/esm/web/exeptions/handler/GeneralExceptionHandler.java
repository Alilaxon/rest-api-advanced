package com.epam.esm.web.exeptions.handler;

import com.epam.esm.model.exception.NoSuchGiftException;
import com.epam.esm.web.exeptions.GiftExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GeneralExceptionHandler {

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public GeneralExceptionHandler(ResourceBundleMessageSource messageSource) {

        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {NoSuchGiftException.class})
    public ResponseEntity<Object> handleNoSuchGiftException(NoSuchGiftException e) {

        HttpStatus notFound = HttpStatus.NOT_FOUND;
        String message = toLocale("NoSuchGiftException",List.of(e.getId()));
        GiftExceptionResponse giftExceptionResponse = new GiftExceptionResponse(message, notFound);

        return new ResponseEntity<>(giftExceptionResponse, notFound);
    }





    private String toLocale(String message) {
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }

    private String toLocale(String message, List<Object> arguments) {
        return messageSource.getMessage(message, arguments.toArray(), LocaleContextHolder.getLocale());
    }
}
