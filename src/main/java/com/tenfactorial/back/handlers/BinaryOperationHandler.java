package com.tenfactorial.back.handlers;

import java.util.LinkedList;

public class BinaryOperationHandler implements WordHandler {
    private final String word;
    private final String opName;
    private final BinaryStackFunction function;
    private WordHandler next;

    public BinaryOperationHandler(String word, String opName, BinaryStackFunction function) {
        this.word = word;
        this.opName = opName;
        this.function = function;
    }

    @Override
    public void handleWord(String word, LinkedList<Integer> stack) {
        if (word.toUpperCase().equals(this.word.toUpperCase())) {
            if (stack.size() < 2) {
                throw new IllegalArgumentException(String.format("%s requires that the stack contain at least 2 values", opName));
            }
            int operand1 = stack.pop();
            int operand2 = stack.pop();
            function.apply(stack, operand1, operand2);
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
