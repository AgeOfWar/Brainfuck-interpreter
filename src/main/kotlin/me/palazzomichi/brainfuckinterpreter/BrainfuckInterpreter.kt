package me.palazzomichi.brainfuckinterpreter

import me.palazzomichi.brainfuckinterpreter.stream.BrainfuckReader
import me.palazzomichi.brainfuckinterpreter.util.brainfuck
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import java.util.*

class BrainfuckInterpreter(
        val cells: ByteArray = ByteArray(30_000),
        val startIndex: Int = 0
) {
    fun execute(program: BrainfuckProgram, input: InputStream, output: OutputStream): OutputStream {
        val context = Context(input, output)
        program.instructions.forEach { it.execute(context) }
        return output
    }
    
    inner class Context(val input: InputStream, val output: OutputStream) {
        val cells = this@BrainfuckInterpreter.cells
        var index = this@BrainfuckInterpreter.startIndex
        
        var currentCell: Byte
            get() = cells[index]
            set(value) {
                cells[index] = value
            }
        
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Context) return false
            
            return Arrays.equals(cells, other.cells) && index == other.index
        }
        
        override fun hashCode() = 31 * Arrays.hashCode(cells) + index
    }
}

val brainfuck = BrainfuckInterpreter()

fun BrainfuckInterpreter.execute(program: BrainfuckReader, input: InputStream, output: OutputStream) = program.use {
    execute(it.readProgram(), input, output)
}

fun BrainfuckInterpreter.execute(program: Reader, input: InputStream, output: OutputStream) =
    execute(program.brainfuck(), input, output)

fun BrainfuckInterpreter.execute(program: String, input: InputStream, output: OutputStream) =
    execute(program.reader(), input, output)