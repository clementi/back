package com.tenfactorial.back.handlers;

import java.util.EmptyStackException;
import java.util.LinkedList;

public interface WordHandler {
    void handleWord(String word, LinkedList<Integer> stack);

    WordHandler attach(WordHandler next);

    static WordHandler buildBuiltins() {
        WordHandler head = new UnaryOperationHandler("DUP", "Duplicating", (s, x) -> {
            s.push(x);
            s.push(x);
        });

        head.attach(new UnaryOperationHandler("DROP", "Dropping", (s, x) -> {}))
                .attach(new BinaryOperationHandler("SWAP", "Swapping", (s, x, y) -> {
                    s.push(x);
                    s.push(y);
                }))
                .attach(new BinaryOperationHandler("OVER", "Overing", (s, x, y) -> {
                    s.push(y);
                    s.push(x);
                    s.push(y);
                }))
                .attach(new BinaryOperationHandler("+", "Addition", (s, x, y) -> s.push(y + x)))
                .attach(new BinaryOperationHandler("-", "Subtraction", (s, x, y) -> s.push(y - x)))
                .attach(new BinaryOperationHandler("*", "Multiplication", (s, x, y) -> s.push(y * x)))
                .attach(new BinaryOperationHandler("/", "Division", (s, x, y) -> {
                    if (x == 0) {
                        throw new IllegalArgumentException("Division by 0 is not allowed");
                    }
                    s.push(y / x);
                }))
                .attach((new UnaryOperationHandler("PRINT", "Printing", (s, x) -> System.out.print(x))))
                .attach((new UnaryOperationHandler("PRINTLN", "Printing with a newline", (s, x) -> System.out.println(x))))
                .attach((new NullaryOperationHandler("CLEAR", LinkedList::clear)))
                .attach((new NullaryOperationHandler(".", (s) -> {
                    if (s.isEmpty()) {
                        throw new EmptyStackException();
                    } else {
                        System.out.println(s.pop());
                    }
                })))
                .attach(new UnknownOperationHandler((word) -> {
                    throw new IllegalArgumentException(String.format("Unknown word '%s'", word));
                }));

        return head;
    }
}
