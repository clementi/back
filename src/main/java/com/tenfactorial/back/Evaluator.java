package com.tenfactorial.back;

import com.tenfactorial.back.exceptions.StackUnderflowException;
import com.tenfactorial.back.handlers.WordHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Evaluator {

    private final LinkedList<Integer> stack = new LinkedList<>();
    private final Map<String, String> userDefinitions = new HashMap<>();

    private final WordHandler handlers = WordHandler.buildBuiltins();

    public List<Integer> evaluateProgram(List<String> program) throws StackUnderflowException {
        evaluate(program);
        Collections.reverse(stack);
        return stack;
    }

    private void evaluate(List<String> program) throws StackUnderflowException {
        for (String line : program) {
            execute(line);
        }
    }

    private void execute(String line) throws StackUnderflowException {
        if (isDefinitionLine(line)) {
            storeDefinition(line);
        } else {
            for (var token : line.split(" ")) {
                handleToken(token);
            }
        }
    }

    private void handleToken(String token) throws StackUnderflowException {
        if (isPrimitive(token)) {
            stack.push(Integer.parseInt(token));
        } else if (isUserDefinedWord(token)) {
            executeUserDefinedWord(token);
        } else if (isBuiltinWord(token)) {
            executeBuiltinWord(token);
        } else {
            throw new IllegalArgumentException(String.format("No definition available for operator \"%s\"", token));
        }
    }

    private void storeDefinition(String line) {
        String[] tokens = line.split(" ");
        String newWord = tokens[1];

        if (isPrimitive(newWord)) {
            throw new IllegalArgumentException("Cannot redefine numbers");
        }

        String definition = Arrays.stream(tokens)
                .skip(2)
                .takeWhile(token -> !token.equals(";"))
                .collect(Collectors.joining(" "));

        userDefinitions.put(newWord.toUpperCase(), expandDefinition(definition.toUpperCase()));
    }

    private String expandDefinition(String definition) {
        String[] tokens = definition.split(" ");
        String[] expandedDefinition = new String[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            expandedDefinition[i] = userDefinitions.getOrDefault(token, token);
        }

        return String.join(" ", expandedDefinition);
    }

    private boolean isDefinitionLine(String line) {
        return line.startsWith(":") && line.endsWith(";");
    }

    private void executeUserDefinedWord(String word) throws StackUnderflowException {
        String definition = userDefinitions.get(word.toUpperCase());
        evaluate(Collections.singletonList(definition));
    }

    private void executeBuiltinWord(String word) throws StackUnderflowException {
        handlers.handleWord(word, stack);
    }

    private boolean isUserDefinedWord(String token) {
        return userDefinitions.keySet().stream()
                .anyMatch(word -> word.equalsIgnoreCase(token));
    }

    private boolean isPrimitive(String token) {
        return token.chars().allMatch(Character::isDigit);
    }

    private boolean isBuiltinWord(String token) {
        return WordHandler.builtins.contains(token.toUpperCase());
    }
}
