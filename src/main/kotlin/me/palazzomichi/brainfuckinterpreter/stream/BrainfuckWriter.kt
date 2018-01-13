package me.palazzomichi.brainfuckinterpreter.stream

import me.palazzomichi.brainfuckinterpreter.BrainfuckProgram
import me.palazzomichi.brainfuckinterpreter.BrainfuckProgram.Instruction
import java.io.Writer

class BrainfuckWriter(private val writer: Writer) : Writer() {
    
    fun writeProgram(program: BrainfuckProgram) {
        program.instructions.forEach(this::writeInstruction)
    }
    
    fun writeInstruction(instruction: Instruction) {
        writer.write(when (instruction) {
            Instruction.Increment -> "+"
            Instruction.Decrement -> "-"
            Instruction.PreviousCell -> "<"
            Instruction.NextCell -> ">"
            Instruction.Write -> "."
            Instruction.Read -> ","
            is Instruction.Loop -> return writeLoop(instruction)
        })
    }
    
    private fun writeLoop(loop: Instruction.Loop) {
        writer.write("[")
        loop.instructions.forEach(this::writeInstruction)
        writer.write("]")
    }
    
    override fun write(buf: CharArray, off: Int, len: Int) = writer.write(buf, off, len)
    
    override fun flush() = writer.flush()
    
    override fun close() = writer.close()
    
}