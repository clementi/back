package com.tenfactorial.back.handlers;

import com.tenfactorial.back.exceptions.StackUnderflowException;
import com.tenfactorial.back.functions.UnaryStackFunction;

import java.util.LinkedList;
import java.util.Set;

public interface WordHandler {
    void handleWord(String word, LinkedList<Integer> stack) throws StackUnderflowException;

    WordHandler attach(WordHandler next);

    Set<String> builtins = Set.of("DUP", "DROP", "SWAP", "OVER", "+", "-", "*", "/", "CLEAR", ".", "PRINT", "PRINTLN", ">", "=", "<");

    static WordHandler buildBuiltins() {
        WordHandler head = new UnaryOperationHandler("DUP", (s, x) -> {
            s.push(x);
            s.push(x);
        });

        UnaryStackFunction printlnFunction = (s, x) -> System.out.println(x);

        head.attach(new UnaryOperationHandler("DROP", (s, x) -> {}))
                .attach(new BinaryOperationHandler("SWAP", (s, x, y) -> {
                    s.push(x);
                    s.push(y);
                }))
                .attach(new BinaryOperationHandler("OVER", (s, x, y) -> {
                    s.push(y);
                    s.push(x);
                    s.push(y);
                }))
                .attach(new BinaryOperationHandler("+", (s, x, y) -> s.push(y + x)))
                .attach(new BinaryOperationHandler("-", (s, x, y) -> s.push(y - x)))
                .attach(new BinaryOperationHandler("*", (s, x, y) -> s.push(y * x)))
                .attach(new BinaryOperationHandler("/", (s, x, y) -> s.push(y / x)))
                .attach(new BinaryOperationHandler(">", (s, x, y) -> {
                    if (y > x) {
                        s.push(1);
                    } else {
                        s.push(0);
                    }
                }))
                .attach(new BinaryOperationHandler("=", (s, x, y) -> {
                    if (y == x) {
                        s.push(1);
                    } else {
                        s.push(0);
                    }
                }))
                .attach(new BinaryOperationHandler("<", (s, x, y) -> {
                    if (y < x) {
                        s.push(1);
                    } else {
                        s.push(0);
                    }
                }))
                .attach(new UnaryOperationHandler("PRINT", (s, x) -> System.out.print(x)))
                .attach(new UnaryOperationHandler("PRINTLN", printlnFunction))
                .attach(new UnaryOperationHandler(".", printlnFunction))
                .attach(new NullaryOperationHandler("CLEAR", LinkedList::clear))
                .attach(new UnknownOperationHandler((word) -> {
                    throw new IllegalArgumentException(String.format("Unknown word '%s'", word));
                }));

        return head;
    }
}
