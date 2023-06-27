package com.epam.esm.model.exception;

public class GiftNameIsReservedException extends Exception {

    private final String name;

    public GiftNameIsReservedException(String name) {
        super("This name="+name+" is reserved");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
