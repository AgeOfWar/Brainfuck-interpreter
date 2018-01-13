package me.palazzomichi.brainfuckinterpreter.test

import me.palazzomichi.brainfuckinterpreter.brainfuck
import org.junit.Assert.assertArrayEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class BrainfuckInterpreterTest {
    
    @Test fun testExecute() {
        val program = program(In, loop(Next, Inc, Inc, Inc, Inc, Prev, Dec), Next, Inc, Out)
        val input = ByteArrayInputStream(byteArrayOf(8))
        val output = ByteArrayOutputStream()
        brainfuck.execute(program, input, output)
        assertArrayEquals(byteArrayOf(33), output.toByteArray())
    }
    
}