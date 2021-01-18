package com.tenfactorial.back.handlers;

import java.util.LinkedList;

public class NullaryOperationHandler implements WordHandler {
    private final String word;
    private final NullaryStackFunction function;
    private WordHandler next;

    public NullaryOperationHandler(String word, NullaryStackFunction function) {
        this.word = word;
        this.function = function;
    }

    @Override
    public void handleWord(String word, LinkedList<Integer> stack) {
        if (word.equalsIgnoreCase(this.word)) {
            function.apply(stack);
        } else {
            if (next != null) {
                next.handleWord(word, stack);
            }
        }
    }

    @Override
    public WordHandler attach(WordHandler next) {
        this.next = next;
        return next;
    }
}
