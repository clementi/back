package com.tenfactorial.back.handlers;

import java.util.LinkedList;

public class UnaryOperationHandler implements WordHandler {
    private final String word;
    private final String opName;
    private final UnaryStackFunction function;
    private WordHandler next;

    public UnaryOperationHandler(String word, String opName, UnaryStackFunction function) {
        this.word = word;
        this.opName = opName;
        this.function = function;
    }

    @Override
    public void handleWord(String word, LinkedList<Integer> stack) {
        if (word.toUpperCase().equals(this.word.toUpperCase())) {
            if (stack.isEmpty()) {
                throw new IllegalArgumentException(String.format("%s requires that the stack contain at least 1 value", opName));
            }
            int operand = stack.pop();
            function.apply(stack, operand);
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
