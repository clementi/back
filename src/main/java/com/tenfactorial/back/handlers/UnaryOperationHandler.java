package com.tenfactorial.back.handlers;

import com.tenfactorial.back.exceptions.StackUnderflowException;
import com.tenfactorial.back.functions.UnaryStackFunction;

import java.util.LinkedList;

public class UnaryOperationHandler implements WordHandler {
    private final String word;
    private final UnaryStackFunction function;
    private WordHandler next;

    public UnaryOperationHandler(String word, UnaryStackFunction function) {
        this.word = word;
        this.function = function;
    }

    @Override
    public void handleWord(String word, LinkedList<Integer> stack) throws StackUnderflowException {
        if (word.equalsIgnoreCase(this.word)) {
            if (stack.isEmpty()) {
                throw new StackUnderflowException();
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
