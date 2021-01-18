package com.tenfactorial.back.exceptions;

public class StackUnderflowException extends Exception {
    public StackUnderflowException() {
        super("Stack underflow");
    }
}
