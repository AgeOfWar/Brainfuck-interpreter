package me.palazzomichi.brainfuckinterpreter

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
data class BrainfuckProgram(val instructions: Array<Instruction>) {
    
    override fun toString(): String {
        val writer = StringWriter()
        writer.brainfuck().writeProgram(this)
        return writer.toString()
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BrainfuckProgram) return false
        return instructions.contentEquals(other.instructions)
    }
    
    override fun hashCode() = instructions.contentHashCode()
    
    operator fun plus(other: BrainfuckProgram) = BrainfuckProgram(instructions + other.instructions)
    operator fun plus(other: Instruction) = BrainfuckProgram(instructions + other)
    
    /**
     * A Brainfuck instruction.
     *
     * @author Michi Palazzo
     */
    sealed class Instruction {
        abstract fun execute(context: BrainfuckInterpreter.Context)
    
        override fun toString(): String {
            val writer = StringWriter()
            writer.brainfuck().writeInstruction(this)
            return writer.toString()
        }
        
        object Increment : Instruction() {
            override fun execute(context: BrainfuckInterpreter.Context) {
                context.currentCell++
            }
        }
        
        object Decrement : Instruction() {
            override fun execute(context: BrainfuckInterpreter.Context) {
                context.currentCell--
            }
        }
        
        object NextCell : Instruction() {
            override fun execute(context: BrainfuckInterpreter.Context) {
                if (context.index == context.cells.lastIndex)
                    throw BrainfuckException("You wanted to go beyond the last cell")
                context.index++
            }
        }
        
        object PreviousCell : Instruction() {
            override fun execute(context: BrainfuckInterpreter.Context) {
                if (context.index == 0)
                    throw BrainfuckException("You wanted to go below the first cell")
                context.index--
            }
        }
        
        data class Loop(val instructions: Array<Instruction>) : Instruction() {
            override fun execute(context: BrainfuckInterpreter.Context) {
                while (context.currentCell != 0.toByte()) {
                    instructions.forEach { it.execute(context) }
                }
            }
    
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is Loop) return false
                return instructions.contentEquals(other.instructions)
            }
    
            override fun hashCode() = instructions.contentHashCode()
        }
        
        object Write : Instruction() {
            override fun execute(context: BrainfuckInterpreter.Context) {
                context.output.write(context.currentCell.toInt())
            }
        }
        
        object Read : Instruction() {
            override fun execute(context: BrainfuckInterpreter.Context) {
                val b = context.input.read()
                context.currentCell = if (b == -1) 0 else b.toByte()
            }
        }
    }
    
}