package com.tenfactorial.back.handlers;

import java.util.LinkedList;

@FunctionalInterface
public interface UnaryStackFunction {
    void apply(LinkedList<Integer> stack, int operand);
}

