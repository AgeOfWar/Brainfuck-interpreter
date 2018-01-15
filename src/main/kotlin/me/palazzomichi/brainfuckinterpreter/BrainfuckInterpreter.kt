package me.palazzomichi.brainfuckinterpreter

import me.palazzomichi.brainfuckinterpreter.stream.BrainfuckReader
import me.palazzomichi.brainfuckinterpreter.util.brainfuck
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader

/**
 * An interpreter for the Brainfuck programming language.
 *
 * @property cells the initial cells at every execution of [execute]
 * @property startIndex the start index at every execution of [execute]
 * @constructor Creates a [BrainfuckInterpreter] with its initial state.
 * @see BrainfuckProgram
 * @author Michi Palazzo
 */
class BrainfuckInterpreter(
        val cells: ByteArray = ByteArray(30_000),
        val startIndex: Int = 0
) {
    /**
     * Executes a [BrainfuckProgram] taking an [input] and storing the result in the [output].
     *
     * Note that this method does **not** flushes the [output].
     *
     * @param program the program to execute
     * @param input   the input of the program
     * @param output  the output that stores the program result
     * @return the [output] argument
     * @throws BrainfuckException if a exception occurs during de program execution,
     * for example if you try to go below the first cell
     */
    fun execute(program: BrainfuckProgram, input: InputStream, output: OutputStream): OutputStream {
        val context = State(BrainfuckProgram.State(cells.copyOf(), startIndex), input, output)
        program.instructions.forEach { it.execute(context) }
        return output
    }
    
    /**
     * The state of the execution of a [BrainfuckProgram].
     *
     * @property state  the program state
     * @property input  input stream of the program
     * @property output the output stream of the program
     */
    data class State(val state: BrainfuckProgram.State,
                     val input: InputStream,
                     val output: OutputStream)
}

/**
 * Exception thrown when a problem occurs during the execution of a [BrainfuckProgram].
 *
 * @see BrainfuckInterpreter
 * @see BrainfuckProgram
 * @author Michi Palazzo
 */
class BrainfuckException(message: String, cause: Throwable? = null) : Exception(message, cause)

/**
 * Standard [BrainfuckInterpreter].
 */
val brainfuck = BrainfuckInterpreter()

/**
 * @see BrainfuckInterpreter.execute
 */
fun BrainfuckInterpreter.execute(program: BrainfuckReader, input: InputStream, output: OutputStream) = program.use {
    execute(it.readProgram(), input, output)
}

/**
 * @see BrainfuckInterpreter.execute
 */
fun BrainfuckInterpreter.execute(program: Reader, input: InputStream, output: OutputStream) =
        execute(program.brainfuck(), input, output)

/**
 * @see BrainfuckInterpreter.execute
 */
fun BrainfuckInterpreter.execute(program: String, input: InputStream, output: OutputStream) =
        execute(program.reader(), input, output)
