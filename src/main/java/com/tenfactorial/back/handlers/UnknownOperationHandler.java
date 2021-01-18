package com.tenfactorial.back.handlers;

import java.util.LinkedList;
import java.util.function.Consumer;

public class UnknownOperationHandler implements WordHandler {
    private final Consumer<String> action;

    public UnknownOperationHandler(Consumer<String> action) {
        this.action = action;
    }

    @Override
    public void handleWord(String word, LinkedList<Integer> stack) {
        action.accept(word);
    }

    @Override
    public WordHandler attach(WordHandler next) {
        return null;
    }
}
