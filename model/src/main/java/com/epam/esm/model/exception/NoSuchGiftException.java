package com.epam.esm.model.exception;

public class NoSuchGiftException extends Exception {
   private final Long id;

    public NoSuchGiftException(Long id) {
        super("No Such Gift by id=" + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
