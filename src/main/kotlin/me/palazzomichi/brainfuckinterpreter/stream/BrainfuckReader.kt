package me.palazzomichi.brainfuckinterpreter.stream

import me.palazzomichi.brainfuckinterpreter.BrainfuckProgram
import me.palazzomichi.brainfuckinterpreter.BrainfuckProgram.Instruction
import java.io.EOFException
import java.io.Reader
import java.io.StreamCorruptedException

/**
 * A character stream that reads a Brainfuck program from a [reader].
 *
 * @property reader the reader
 * @author Michi Palazzo
 */
class BrainfuckReader(private val reader: Reader) : Reader() {
    
    /**
     * Reads a program from the stream.
     *
     * @return the read program
     */
    fun readProgram(): BrainfuckProgram {
        val instructions = mutableListOf<Instruction>()
        try {
            while (true) {
                instructions += readInstruction()
            }
        } catch (e: EOFException) {
            return BrainfuckProgram(instructions)
        }
    }
    
    /**
     * Reads an instruction from the stream.
     *
     * @return the read instruction
     */
    tailrec fun readInstruction(): Instruction {
        return when (reader.read()) {
            '+'.toInt() -> Instruction.Increment
            '-'.toInt() -> Instruction.Decrement
            '<'.toInt() -> Instruction.PreviousCell
            '>'.toInt() -> Instruction.NextCell
            '.'.toInt() -> Instruction.Write
            ','.toInt() -> Instruction.Read
            '['.toInt() -> readLoop()
            ']'.toInt() -> throw StreamCorruptedException("Unmatched ']'")
            -1 -> throw EOFException()
            else -> readInstruction()
        }
    }
    
    private fun readLoop(): Instruction.Loop {
        val instructions = mutableListOf<Instruction>()
        try {
            while (true) {
                instructions += readInstruction()
            }
        } catch (e: StreamCorruptedException) {
            return Instruction.Loop(instructions)
        } catch (e: EOFException) {
            throw StreamCorruptedException("Unmatched '['")
        }
    }
    
    override fun read(buf: CharArray, off: Int, len: Int) = reader.read(buf, off, len)
    
    override fun close() = reader.close()
    
}