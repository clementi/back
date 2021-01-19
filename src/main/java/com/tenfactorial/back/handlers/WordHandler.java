package com.tenfactorial.back.handlers;

import com.tenfactorial.back.exceptions.StackUnderflowException;
import com.tenfactorial.back.functions.UnaryStackFunction;

import java.util.LinkedList;

import static com.tenfactorial.back.BuiltinWord.*;

public interface WordHandler {
    void handleWord(String word, LinkedList<Integer> stack) throws StackUnderflowException;

    WordHandler attach(WordHandler next);

    static WordHandler buildBuiltins() {
        WordHandler head = new UnaryOperationHandler(DUP, (s, x) -> {
            s.push(x);
            s.push(x);
        });

        UnaryStackFunction printlnFunction = (s, x) -> System.out.println(x);

        head.attach(new UnaryOperationHandler(DROP, (s, x) -> {}))
                .attach(new BinaryOperationHandler(SWAP, (s, x, y) -> {
                    s.push(x);
                    s.push(y);
                }))
                .attach(new BinaryOperationHandler(OVER, (s, x, y) -> {
                    s.push(y);
                    s.push(x);
                    s.push(y);
                }))
                .attach(new BinaryOperationHandler(PLUS, (s, x, y) -> s.push(y + x)))
                .attach(new BinaryOperationHandler(MINUS, (s, x, y) -> s.push(y - x)))
                .attach(new BinaryOperationHandler(STAR, (s, x, y) -> s.push(y * x)))
                .attach(new BinaryOperationHandler(SLASH, (s, x, y) -> s.push(y / x)))
                .attach(new BinaryOperationHandler(GREATER_THAN, (s, x, y) -> {
                    if (y > x) {
                        s.push(1);
                    } else {
                        s.push(0);
                    }
                }))
                .attach(new BinaryOperationHandler(EQUALS, (s, x, y) -> {
                    if (y == x) {
                        s.push(1);
                    } else {
                        s.push(0);
                    }
                }))
                .attach(new BinaryOperationHandler(LESS_THAN, (s, x, y) -> {
                    if (y < x) {
                        s.push(1);
                    } else {
                        s.push(0);
                    }
                }))
                .attach(new BinaryOperationHandler(AND, (s, x, y) -> {
                    if (y == 0) {
                        s.push(0);
                    } else {
                        s.push(x);
                    }
                }))
                .attach(new BinaryOperationHandler(OR, (s, x, y) -> {
                    if (y != 0) {
                        s.push(y);
                    } else {
                        s.push(x);
                    }
                }))
                .attach(new UnaryOperationHandler(NOT, (s, x) -> {
                    if (x == 0) {
                        s.push(1);
                    } else {
                        s.push(0);
                    }
                }))
                .attach(new UnaryOperationHandler(PRINT, (s, x) -> System.out.print(x)))
                .attach(new UnaryOperationHandler(PRINTLN, printlnFunction))
                .attach(new UnaryOperationHandler(DOT, printlnFunction))
                .attach(new NullaryOperationHandler(CLEAR, LinkedList::clear))
                .attach(new UnknownOperationHandler((word) -> {
                    throw new IllegalArgumentException(String.format("Unknown word '%s'", word));
                }));

        return head;
    }
}
