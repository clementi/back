package com.tenfactorial.back.handlers;

import com.tenfactorial.back.exceptions.StackUnderflowException;
import com.tenfactorial.back.functions.BinaryStackFunction;

import java.util.LinkedList;

public class BinaryOperationHandler implements WordHandler {
    private final String word;
    private final BinaryStackFunction function;
    private WordHandler next;

    public BinaryOperationHandler(String word, BinaryStackFunction function) {
        this.word = word;
        this.function = function;
    }

    @Override
    public void handleWord(String word, LinkedList<Integer> stack) throws StackUnderflowException {
        if (word.equalsIgnoreCase(this.word)) {
            if (stack.size() < 2) {
                throw new StackUnderflowException();
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
