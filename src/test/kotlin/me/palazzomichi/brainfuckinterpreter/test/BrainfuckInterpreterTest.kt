package me.palazzomichi.brainfuckinterpreter.test

import me.palazzomichi.brainfuckinterpreter.BrainfuckException
import me.palazzomichi.brainfuckinterpreter.BrainfuckInterpreter
import me.palazzomichi.brainfuckinterpreter.brainfuck
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
        brainfuck.execute(program, input, output)
        assertArrayEquals(byteArrayOf(33), output.toByteArray())
    }
    
    @Test fun testExecuteIndexBelowFirstCell() {
        val program = program(Next, Prev, Prev)
        assertFailsWith(BrainfuckException::class) {
            brainfuck.execute(program, EmptyInputStream, EmptyOutputStream)
        }
    }
    
    @Test fun testExecuteIndexBeyondLastCell() {
        val program = program(Next, Next, Next)
        assertFailsWith(BrainfuckException::class) {
            BrainfuckInterpreter(ByteArray(3)).execute(program, EmptyInputStream, EmptyOutputStream)
        }
    }
    
    @Test fun testExecuteTwoTimes() {
        val loop = loop(Next, Inc, Inc, Next, Inc, Prev, Prev, Dec)
        val program = program(In, loop, Next, Inc, Out, Next, Out)
        val input = ByteArray(1) {3}
        val input1 = ByteArrayInputStream(input)
        val input2 = ByteArrayInputStream(input)
        val output1 = ByteArrayOutputStream()
        val output2 = ByteArrayOutputStream()
        brainfuck.execute(program, input1, output1)
        brainfuck.execute(program, input2, output2)
        assertArrayEquals(output1.toByteArray(), output2.toByteArray())
    }
    
}