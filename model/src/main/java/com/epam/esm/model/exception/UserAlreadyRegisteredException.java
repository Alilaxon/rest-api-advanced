package com.epam.esm.model.exception;

public class UserAlreadyRegisteredException extends Exception{

    private final String name;

    public UserAlreadyRegisteredException(String name) {
        super("This name="+name+" is reserved");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
