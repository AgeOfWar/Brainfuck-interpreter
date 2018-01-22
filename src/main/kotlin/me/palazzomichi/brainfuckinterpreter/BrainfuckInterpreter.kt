package me.palazzomichi.brainfuckinterpreter

import java.io.InputStream
import java.io.OutputStream

/**
 * An interpreter for the Brainfuck programming language.
 *
 * @property configuration the configuration of the interpreter
 * @see BrainfuckProgram
 * @author Michi Palazzo
 */
class BrainfuckInterpreter(private val configuration: Configuration) {
    
    /**
     * @param inputStream  input stream of the program
     * @param outputStream the output stream of the program
     */
    constructor(inputStream: InputStream, outputStream: OutputStream) :
            this(Configuration(inputStream, outputStream))
    
    /**
     * Executes a [BrainfuckProgram] taking an [input][Configuration.inputStream] and storing the
     * result in the [output][Configuration.outputStream] using the [cells] argument as the program
     * cells.
     *
     * Note that this method does **not** flushes the [output][Configuration.outputStream].
     *
     * @param program       the program to execute
     * @param cells         the cell iterator used by the program
     * @throws BrainfuckException if a exception occurs during de program execution,
     * for example if you try to go below the first cell
     */
    fun execute(program: BrainfuckProgram, cells: CellIterator = CellIterator.default()) {
        program.instructions.forEach { it.execute(cells, configuration) }
    }
    
    /**
     * @property inputStream  input stream of the program
     * @property outputStream the output stream of the program
     */
    data class Configuration(val inputStream: InputStream, val outputStream: OutputStream)
}
