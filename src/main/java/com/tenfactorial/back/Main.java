package com.tenfactorial.back;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            new Repl().run();
        } else {
            new Executor().execute(args[0]);
        }
    }
}
