package me.palazzomichi.brainfuckinterpreter.test

import me.palazzomichi.brainfuckinterpreter.BrainfuckException
import me.palazzomichi.brainfuckinterpreter.BrainfuckInterpreter
import me.palazzomichi.brainfuckinterpreter.cellIterator
import me.palazzomichi.brainfuckinterpreter.util.EmptyInputStream
import me.palazzomichi.brainfuckinterpreter.util.EmptyOutputStream
import org.junit.Assert.assertArrayEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.test.assertFailsWith

class BrainfuckInterpreterTest {
    
    @Test fun testExecute() {
        val program = program(In, loop(Next, Inc, Inc, Inc, Inc, Prev, Dec), Next, Inc, Out)
        val input = ByteArrayInputStream(byteArrayOf(8))
        val output = ByteArrayOutputStream()
        val brainfuck = BrainfuckInterpreter(input, output)
        brainfuck.execute(program)
        assertArrayEquals(byteArrayOf(33), output.toByteArray())
    }
    
    @Test fun testExecuteIndexBelowFirstCell() {
        val program = program(Next, Prev, Prev)
        val brainfuck = BrainfuckInterpreter(EmptyInputStream, EmptyOutputStream)
        assertFailsWith(BrainfuckException::class) {
            brainfuck.execute(program)
        }
    }
    
    @Test fun testExecuteIndexBeyondLastCell() {
        val program = program(Next, Next, Next)
        val brainfuck = BrainfuckInterpreter(EmptyInputStream, EmptyOutputStream)
        assertFailsWith(BrainfuckException::class) {
            brainfuck.execute(program, ByteArray(3).cellIterator())
        }
    }
    
    @Test fun testExecuteNTimes() {
        val times = 10
        val loop = loop(Next, Inc, Inc, Prev, Dec)
        val program = program(In, loop, Next, Inc, Out)
        val input = ByteArrayInputStream(ByteArray(times) {3})
        val output = ByteArrayOutputStream()
        val brainfuck = BrainfuckInterpreter(input, output)
        (1..times).forEach { brainfuck.execute(program) }
        assertArrayEquals(ByteArray(times) {7}, output.toByteArray())
    }
    
}