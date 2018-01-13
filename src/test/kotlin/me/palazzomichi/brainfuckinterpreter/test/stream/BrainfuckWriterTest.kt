package me.palazzomichi.brainfuckinterpreter.test.stream

import me.palazzomichi.brainfuckinterpreter.test.*
import me.palazzomichi.brainfuckinterpreter.util.brainfuck
import org.junit.Test
import java.io.StringWriter
import kotlin.test.assertEquals

class BrainfuckWriterTest {
    
    @Test fun testWriteProgram() {
        val program = program(In, loop(Next, Inc, Inc, Prev, Dec), Next, Out)
        val writer = StringWriter()
        writer.brainfuck().writeProgram(program)
        assertEquals(",[>++<-]>.", writer.toString())
    }
    
}