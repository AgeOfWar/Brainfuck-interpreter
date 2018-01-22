package me.palazzomichi.brainfuckinterpreter.util

import me.palazzomichi.brainfuckinterpreter.BrainfuckException
import me.palazzomichi.brainfuckinterpreter.BrainfuckInterpreter
import me.palazzomichi.brainfuckinterpreter.BrainfuckInterpreter.Configuration
import me.palazzomichi.brainfuckinterpreter.BrainfuckProgram
import me.palazzomichi.brainfuckinterpreter.CellIterator
import me.palazzomichi.brainfuckinterpreter.stream.BrainfuckReader
import java.io.Reader

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
fun BrainfuckInterpreter.execute(program: BrainfuckReader,
                                 cells: CellIterator = CellIterator.default()) = program.use {
    execute(it.readProgram(), cells)
}

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
fun BrainfuckInterpreter.execute(program: Reader, cells: CellIterator = CellIterator.default()) =
        execute(program.brainfuck(), cells)

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
fun BrainfuckInterpreter.execute(program: String, cells: CellIterator = CellIterator.default()) =
        execute(program.reader(), cells)
