package com.tenfactorial.back;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Repl {
    private final Properties properties;

    public Repl() throws IOException {
        this.properties = loadProperties();
    }

    public void run() throws IOException {
        System.out.printf("%s %s%n", properties.getProperty("name"), properties.getProperty("version"));
        System.out.println("Enter :quit or :q to exit");

        var reader = new BufferedReader(new InputStreamReader(System.in));

        var evaluator = new Evaluator();

        var run = 1;
        List<Integer> stack = new LinkedList<>();

        printPrompt(run);
        String line = reader.readLine();

        while (!isQuit(line)) {
            if (line.equals("")) {
                printStack(stack);
                printPrompt(++run);
                line = reader.readLine();
                continue;
            }

            var listLine = Collections.singletonList(line);
            try {
                stack = evaluator.evaluateProgram(listLine);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            printStack(stack);
            printPrompt(++run);
            line = reader.readLine();
        }
    }

    private Properties loadProperties() throws IOException {
        var properties = new Properties();
        var propertiesFileName = "config.properties";

        var inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

        if (inputStream != null) {
            properties.load(inputStream);
            return properties;
        } else {
            throw new FileNotFoundException("properties not found");
        }
    }

    private void printPrompt(int run) {
        System.out.printf("%s [%03d]> ", properties.getProperty("name").toLowerCase(), run);
    }

    private static void printStack(List<Integer> stack) {
        if (!stack.isEmpty()) {
            System.out.println("\n--- Data stack:");
            for (int i = stack.size() - 1; i >= 0; i--) {
                System.out.println(stack.get(i));
            }
        }
    }

    private static boolean isQuit(@NotNull String line) {
        return line.equalsIgnoreCase(":quit") || line.equalsIgnoreCase(":q");
    }
}
