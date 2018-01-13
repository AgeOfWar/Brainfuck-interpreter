# Brainfuck Interpreter
kotlin interpreter for the brainfuck programing language.

## About Brainfuck
Any character other than `> <+ -., []` is ignored.
Branfuck is characterized by an array of 30,000 cells initialized to zero
and from a pointer pointing to the current cell.

There are eight commands:
- `+`: Increases the value of the current cell by one.
- `-`: Decrements the value of the current cell by one.
- `>`: Move the pointer to the next cell (on the right).
- `<`: Move the pointer to the cell earlier (on the left).
- `.`: Print the ASCII value of the current cell. (ex: 65 = 'A')
- `,`: Read a single character as input and save it in the current cell.
- `[`: If the cell value is zero, it continues up to the corresponding]. Otherwise, move on to the next instruction.
- `]`: If the cell value is zero, move to the next instruction. If not, go back to the [correspondent.

## Use as a binary
This runs a single Brainfuck file specified as a command-line parameter. Standard in and out are passed to the interpreter.

## Use as a library
### Kotlin
```kotlin
import me.palazzomichi.brainfuckinterpreter.brainfuck

fun main(args: Array<String>) {
    // program can be either a BrainfuckProgram, a BrainfuckReader, a Reader or a String.
    brainfuck.execute(program, inputStream, outputStream)
}
```
### Java
```java
import static me.palazzomichi.brainfuckinterpreter.BrainfuckInterpreter.brainfuck;

public class BrainfuckTest {
  public static void main(String... args) {
    // program can be either a BrainfuckProgram, a BrainfuckReader, a Reader or a String.
    brainfuck.execute(program, inputStream, outputStream);
  }
}
```
