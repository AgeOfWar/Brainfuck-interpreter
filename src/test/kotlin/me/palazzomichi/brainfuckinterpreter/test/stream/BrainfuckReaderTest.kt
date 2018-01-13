package me.palazzomichi.brainfuckinterpreter.test.stream

import me.palazzomichi.brainfuckinterpreter.stream.BrainfuckReader
import me.palazzomichi.brainfuckinterpreter.test.*
import me.palazzomichi.brainfuckinterpreter.util.brainfuck
import org.junit.Test
import java.io.StreamCorruptedException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BrainfuckReaderTest {
    
    @Test fun testReadProgram() {
        val program = "+[+[-]]<>,.".reader()
        val reader = program.brainfuck()
        assertEquals(
                program(Inc, loop(Inc, loop(Dec)), Prev, Next, In, Out),
                reader.readProgram()
        )
    }
    
    @Test fun testReadProgramUnclosedLoop() {
        val program = "++++++++[>++++++++<->.".reader()
        val reader = program.brainfuck()
        assertFailsWith(StreamCorruptedException::class) {
            reader.readProgram()
        }
    }
    
    @Test fun testReadProgramMalformedLoop() {
        val program = "++++++++>++++++++<-]>.".reader()
        val reader = BrainfuckReader(program)
        assertFailsWith(StreamCorruptedException::class) {
            reader.readProgram()
        }
    }
    
}