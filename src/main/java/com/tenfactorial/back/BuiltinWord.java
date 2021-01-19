package com.tenfactorial.back;

public enum BuiltinWord {
    DUP("DUP"),
    DROP("DROP"),
    SWAP("SWAP"),
    OVER("OVER"),
    PLUS("+"),
    MINUS("-"),
    STAR("*"),
    SLASH("/"),
    CLEAR("CLEAR"),
    DOT("."),
    PRINT("PRINT"),
    PRINTLN("PRINTLN"),
    GREATER_THAN(">"),
    EQUALS("="),
    LESS_THAN("<"),
    AND("AND"),
    OR("OR"),
    NOT("NOT");

    private final String value;

    BuiltinWord(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
