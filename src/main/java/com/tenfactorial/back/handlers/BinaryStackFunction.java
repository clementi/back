package com.tenfactorial.back.handlers;

import java.util.LinkedList;

@FunctionalInterface
public interface BinaryStackFunction {
    void apply(LinkedList<Integer> stack, int operand1, int operand2);
}
