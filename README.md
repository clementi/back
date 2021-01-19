# Back

Back is a very basic evaluator for a Forth-like language.

The evaluator supports the following built-in words:

- `+`, `-`, `*`, `/` (integer arithmetic)
- `DUP`, `DROP`, `SWAP`, `OVER`, `CLEAR`, `.` (stack manipulation)
- `>`, `=`, `<` (integer comparisons)
- `AND`, `OR`, `NOT` (boolean operators) 
- `PRINT`, `PRINTLN` (console output)

The evaluator supports defining new words using the
customary syntax: `: word-name definition ;`.

The only data type that is currently supported is 32-bit signed integers.
The integer comparison words evaluate to integers (`1` for true, `0` for false).

Words are case-insensitive.

# Running the tests

You can run all the tests for an exercise by entering the following in your
terminal:

```sh
$ gradle test
```

Use `gradlew.bat` if you're on Windows.

# Running the evaluator

```sh
$ gradle jar && java -jar ./build/libs/back.jar
```
Or you can use the provided script:
```sh
$ # *nix Shell
$ ./back
```
```powershell
> # PowerShell
> .\back
```

# Origin

Back originated as a solution to the "Forth" exercise at [exercism](https://exercism.io).