package com.tenfactorial.back.functions;

import java.util.LinkedList;

@FunctionalInterface
public interface UnaryStackFunction {
    void apply(LinkedList<Integer> stack, int operand);
}

