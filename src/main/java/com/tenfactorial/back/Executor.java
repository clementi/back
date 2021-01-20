package com.tenfactorial.back;

import com.tenfactorial.back.exceptions.StackUnderflowException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class Executor {
    public void execute(String filename) throws IOException {
        var evaluator = new Evaluator();

        try (var reader = new BufferedReader(new FileReader(filename))) {
            evaluator.evaluateProgram(reader.lines().collect(Collectors.toList()));
        } catch (StackUnderflowException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
