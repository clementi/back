package com.tenfactorial.back.handlers;

import java.util.LinkedList;

@FunctionalInterface
public interface NullaryStackFunction {
    void apply(LinkedList<Integer> stack);
}
