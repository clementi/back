package com.tenfactorial.back;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            new Repl().run();
        } else {
            // TODO: Execute a file
        }
    }
}
