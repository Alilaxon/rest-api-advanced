package com.epam.esm.web.exeptions.handler;

import com.epam.esm.model.exception.*;
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
        String message = toLocale("NoSuchGiftException", List.of(e.getId()));
        GiftExceptionResponse giftExceptionResponse = new GiftExceptionResponse(message, notFound);

        return new ResponseEntity<>(giftExceptionResponse, notFound);
    }

    @ExceptionHandler(value = {GiftNameIsReservedException.class})
    public ResponseEntity<Object> handleNoSuchGiftException(GiftNameIsReservedException e) {
        HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        String message = toLocale("GiftNameIsReservedException", List.of(e.getName()));
        GiftExceptionResponse giftExceptionResponse = new GiftExceptionResponse(message, notAcceptable);

        return new ResponseEntity<>(giftExceptionResponse, notAcceptable);
    }

    @ExceptionHandler(value = {InvalidGiftDtoException.class})
    public ResponseEntity<Object> handleNoSuchGiftException(InvalidGiftDtoException e) {
        HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        String message = toLocale("InvalidGiftDtoException");
        GiftExceptionResponse giftExceptionResponse = new GiftExceptionResponse(message, notAcceptable);

        return new ResponseEntity<>(giftExceptionResponse, notAcceptable);
    }

    @ExceptionHandler(value = {NoSuchTagException.class})
    public ResponseEntity<Object> handleNoSuchTagException(NoSuchTagException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        String message = toLocale("NoSuchTagException", List.of(e.getId()));
        GiftExceptionResponse giftExceptionResponse = new GiftExceptionResponse(message, notFound);

        return new ResponseEntity<>(giftExceptionResponse, notFound);
    }

    @ExceptionHandler(value = {NoSuchTagNameException.class})
    public ResponseEntity<Object> handleNoSuchTagException(NoSuchTagNameException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        String message = toLocale("NoSuchTagNameException", List.of(e.getName()));
        GiftExceptionResponse giftExceptionResponse = new GiftExceptionResponse(message, notFound);

        return new ResponseEntity<>(giftExceptionResponse, notFound);
    }

    @ExceptionHandler(value = {TagNameIsReservedException.class})
    public ResponseEntity<Object> handleNoSuchTagException(TagNameIsReservedException e) {
        HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        String message = toLocale("TagNameIsReservedException", List.of(e.getName()));
        GiftExceptionResponse giftExceptionResponse = new GiftExceptionResponse(message, notAcceptable);

        return new ResponseEntity<>(giftExceptionResponse, notAcceptable);
    }

    @ExceptionHandler(value = {InvalidTagDtoException.class})
    public ResponseEntity<Object> handleNoSuchTagException(InvalidTagDtoException e) {
        HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        String message = toLocale("InvalidTagDtoException");
        GiftExceptionResponse giftExceptionResponse = new GiftExceptionResponse(message, notAcceptable);

        return new ResponseEntity<>(giftExceptionResponse, notAcceptable);
    }

    @ExceptionHandler(value = {NoSuchUserException.class})
    public ResponseEntity<Object> handleNoSuchUserException(NoSuchUserException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        String message = toLocale("NoSuchUserException", List.of(e.getId()));
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
