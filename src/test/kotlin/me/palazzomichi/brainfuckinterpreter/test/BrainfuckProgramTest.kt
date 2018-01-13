package me.palazzomichi.brainfuckinterpreter.test

import me.palazzomichi.brainfuckinterpreter.BrainfuckInterpreter
import me.palazzomichi.brainfuckinterpreter.BrainfuckProgram
import me.palazzomichi.brainfuckinterpreter.util.EmptyInputStream
import me.palazzomichi.brainfuckinterpreter.util.EmptyOutputStream
import org.junit.Assert.assertArrayEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.OutputStream
import kotlin.test.assertEquals
import kotlin.test.fail

typealias Inc = BrainfuckProgram.Instruction.Increment
typealias Dec = BrainfuckProgram.Instruction.Decrement
typealias Next = BrainfuckProgram.Instruction.NextCell
typealias Prev = BrainfuckProgram.Instruction.PreviousCell
typealias Loop = BrainfuckProgram.Instruction.Loop
typealias Out = BrainfuckProgram.Instruction.Write
typealias In = BrainfuckProgram.Instruction.Read

fun program(vararg instructions: BrainfuckProgram.Instruction) =
        BrainfuckProgram(arrayOf(*instructions))

fun loop(vararg instructions: BrainfuckProgram.Instruction) =
        Loop(arrayOf(*instructions))

class BrainfuckProgramTest {
    
    class IncrementTest {
        @Test fun testExecute() {
            val context = BrainfuckInterpreter(byteArrayOf(1, 0)).Context(EmptyInputStream, EmptyOutputStream)
            Inc.execute(context)
            assertArrayEquals(byteArrayOf(2, 0), context.cells)
        }
    }
    
    class DecrementTest {
        @Test fun testExecute() {
            val context = BrainfuckInterpreter(byteArrayOf(1, 0)).Context(EmptyInputStream, EmptyOutputStream)
            Dec.execute(context)
            assertArrayEquals(byteArrayOf(0, 0), context.cells)
        }
    }
    
    class NextCellTest {
        @Test fun testExecute() {
            val context = BrainfuckInterpreter(byteArrayOf(1, 0)).Context(EmptyInputStream, EmptyOutputStream)
            Next.execute(context)
            assertEquals(0, context.currentCell)
        }
    }
    
    class PreviousCellTest {
        @Test fun testExecute() {
            val context = BrainfuckInterpreter(byteArrayOf(1, 0), 1).Context(EmptyInputStream, EmptyOutputStream)
            Prev.execute(context)
            assertEquals(1, context.currentCell)
        }
    }
    
    class LoopTest {
        @Test fun testExecute() {
            val output = object : OutputStream() {
                var iterations = 0.toByte()
                override fun write(b: Int) {
                    iterations++
                }
            }
            val iterations = 5.toByte()
            val context = BrainfuckInterpreter(byteArrayOf(iterations, 0)).Context(EmptyInputStream, output)
            Loop(arrayOf(Out, Dec)).execute(context)
            assertEquals(iterations, output.iterations)
        }
        
        @Test fun testExecuteNoIterations() {
            val output = object : OutputStream() {
                override fun write(b: Int) = fail()
            }
            val context = BrainfuckInterpreter(byteArrayOf(1, 0), 1).Context(EmptyInputStream, output)
            loop(Out).execute(context)
        }
    }
    
    class WriteTest {
        @Test fun testExecute() {
            val value = 7.toByte()
            val output = object : OutputStream() {
                override fun write(b: Int) = if (b != value.toInt()) fail() else Unit
            }
            val context = BrainfuckInterpreter(byteArrayOf(value, 0)).Context(EmptyInputStream, output)
            Out.execute(context)
        }
    }
    
    class ReadTest {
        @Test fun testExecute() {
            val value = 7.toByte()
            val input = ByteArrayInputStream(byteArrayOf(value))
            val context = BrainfuckInterpreter(byteArrayOf(1, 0)).Context(input, EmptyOutputStream)
            In.execute(context)
            assertArrayEquals(byteArrayOf(value, 0), context.cells)
        }
    
        @Test fun testExecuteEmptyStream() {
            val context = BrainfuckInterpreter(byteArrayOf(1, 0)).Context(EmptyInputStream, EmptyOutputStream)
            In.execute(context)
            assertArrayEquals(byteArrayOf(0, 0), context.cells)
        }
    }
    
}