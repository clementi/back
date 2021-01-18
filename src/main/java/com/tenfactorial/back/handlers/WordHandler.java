package com.tenfactorial.back.handlers;

import com.tenfactorial.back.functions.UnaryStackFunction;

import java.util.LinkedList;
import java.util.Set;

public interface WordHandler {
    void handleWord(String word, LinkedList<Integer> stack);

    WordHandler attach(WordHandler next);

    Set<String> builtins = Set.of("DUP", "DROP", "SWAP", "OVER", "+", "-", "*", "/", "CLEAR", ".", "PRINT", "PRINTLN");

    static WordHandler buildBuiltins() {
        WordHandler head = new UnaryOperationHandler("DUP", "Duplicating", (s, x) -> {
            s.push(x);
            s.push(x);
        });

        UnaryStackFunction printlnFunction = (s, x) -> System.out.println(x);

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
                .attach((new UnaryOperationHandler("PRINTLN", "Printing with a newline", printlnFunction)))
                .attach((new UnaryOperationHandler(".", "Printing with a newline", printlnFunction)))
                .attach((new NullaryOperationHandler("CLEAR", LinkedList::clear)))
                .attach(new UnknownOperationHandler((word) -> {
                    throw new IllegalArgumentException(String.format("Unknown word '%s'", word));
                }));

        return head;
    }
}
