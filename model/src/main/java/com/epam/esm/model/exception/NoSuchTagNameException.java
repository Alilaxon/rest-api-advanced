package com.epam.esm.model.exception;

public class NoSuchTagNameException extends Exception{

    private final String name;

    public NoSuchTagNameException( String name) {
        super("No Such Tag by name=" + name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
