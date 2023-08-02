package com.epam.esm.web.exeptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public class ExceptionResponse {
    int errorCode;
    String message;
    @JsonIgnore
    HttpStatus httpStatus;

    public ExceptionResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.errorCode = errorCode(httpStatus);
    }

    private int errorCode(HttpStatus httpStatus){

        return httpStatus.value() * 1000 + 10 % 1000;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
