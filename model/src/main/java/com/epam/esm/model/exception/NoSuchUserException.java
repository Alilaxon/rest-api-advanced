package com.epam.esm.model.exception;

public class NoSuchUserException extends Exception {

    private final Long id;

    public NoSuchUserException( Long id) {
        super("No Such User by id=" + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
