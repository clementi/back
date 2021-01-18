package com.tenfactorial.back.functions;

import java.util.LinkedList;

@FunctionalInterface
public interface NullaryStackFunction {
    void apply(LinkedList<Integer> stack);
}
