package com.epam.esm.model.exception;

public class NoSuchTagException extends Exception {

    private final Long id;

    public NoSuchTagException( Long id) {
        super("No Such Tag by id=" + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
