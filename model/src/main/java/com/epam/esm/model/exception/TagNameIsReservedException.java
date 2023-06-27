package com.epam.esm.model.exception;

public class TagNameIsReservedException extends Exception {

    private final String name;

    public TagNameIsReservedException(String name) {
        super("This name="+name+" is reserved");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
