package com.tenfactorial.back.handlers;

import com.tenfactorial.back.BuiltinWord;
import com.tenfactorial.back.exceptions.StackUnderflowException;
import com.tenfactorial.back.functions.NullaryStackFunction;

import java.util.LinkedList;

public class NullaryOperationHandler implements WordHandler {
    private final BuiltinWord word;
    private final NullaryStackFunction function;
    private WordHandler next;

    public NullaryOperationHandler(BuiltinWord word, NullaryStackFunction function) {
        this.word = word;
        this.function = function;
    }

    @Override
    public void handleWord(String word, LinkedList<Integer> stack) throws StackUnderflowException {
        if (word.equalsIgnoreCase(this.word.toString())) {
            function.apply(stack);
        } else if (next != null) {
            next.handleWord(word, stack);
        }
    }

    @Override
    public WordHandler attach(WordHandler next) {
        this.next = next;
        return next;
    }
}
