package me.palazzomichi.brainfuckinterpreter

import me.palazzomichi.brainfuckinterpreter.BrainfuckInterpreter.Configuration
import me.palazzomichi.brainfuckinterpreter.BrainfuckProgram.Instruction
import me.palazzomichi.brainfuckinterpreter.util.brainfuck
import java.io.StringWriter

/**
 * A sequence of [brainfuck instructions][Instruction].
 *
 * @property instructions the sequence of instructions
 * @constructor Creates a [BrainfuckProgram].
 * @see BrainfuckInterpreter
 * @see me.palazzomichi.brainfuckinterpreter.stream.BrainfuckReader
 * @see me.palazzomichi.brainfuckinterpreter.stream.BrainfuckWriter
 * @author Michi Palazzo
 */
data class BrainfuckProgram(val instructions: List<Instruction>) {
    override fun toString(): String {
        val writer = StringWriter()
        writer.brainfuck().writeProgram(this)
        return writer.toString()
    }
    
    operator fun plus(other: BrainfuckProgram) = BrainfuckProgram(instructions + other.instructions)
    operator fun plus(other: Instruction) = BrainfuckProgram(instructions + other)
    
    /**
     * A Brainfuck instruction.
     *
     * @author Michi Palazzo
     */
    sealed class Instruction {
        abstract fun execute(cells: CellIterator, configuration: Configuration)
    
        override fun toString(): String {
            val writer = StringWriter()
            writer.brainfuck().writeInstruction(this)
            return writer.toString()
        }
        
        object Increment : Instruction() {
            override fun execute(cells: CellIterator, configuration: Configuration) {
                cells.current++
            }
        }
        
        object Decrement : Instruction() {
            override fun execute(cells: CellIterator, configuration: Configuration) {
                cells.current--
            }
        }
        
        object NextCell : Instruction() {
            override fun execute(cells: CellIterator, configuration: Configuration) {
                if (!cells.hasNext())
                    throw BrainfuckException("You wanted to go beyond the last cell")
                cells.next()
            }
        }
        
        object PreviousCell : Instruction() {
            override fun execute(cells: CellIterator, configuration: Configuration) {
                if (!cells.hasPrevious())
                    throw BrainfuckException("You wanted to go below the first cell")
                cells.previous()
            }
        }
        
        data class Loop(val instructions: List<Instruction>) : Instruction() {
            override fun execute(cells: CellIterator, configuration: Configuration) {
                while (cells.current != 0.toByte()) {
                    instructions.forEach { it.execute(cells, configuration) }
                }
            }
        }
        
        object Write : Instruction() {
            override fun execute(cells: CellIterator, configuration: Configuration) {
                configuration.outputStream.write(cells.current.toInt())
            }
        }
        
        object Read : Instruction() {
            override fun execute(cells: CellIterator, configuration: Configuration) {
                val b = configuration.inputStream.read()
                cells.current = if (b == -1) 0 else b.toByte()
            }
        }
    }
}