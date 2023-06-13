package com.epam.esm.web.exeptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class RequestException extends RuntimeException {
    private String message;
    private Throwable throwable;
    private HttpStatus httpStatus;
    private ZonedDateTime time;

    public RequestException(String message,
                            Throwable throwable,
                            HttpStatus httpStatus,
                            ZonedDateTime time) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTime() {
        return time;
    }
}
