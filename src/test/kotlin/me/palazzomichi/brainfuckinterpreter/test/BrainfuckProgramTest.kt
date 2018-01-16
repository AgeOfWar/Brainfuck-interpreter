package me.palazzomichi.brainfuckinterpreter.test

import me.palazzomichi.brainfuckinterpreter.BrainfuckInterpreter
import me.palazzomichi.brainfuckinterpreter.BrainfuckProgram
import me.palazzomichi.brainfuckinterpreter.util.EmptyInputStream
import me.palazzomichi.brainfuckinterpreter.util.EmptyOutputStream
import org.junit.Assert.assertArrayEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
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
        BrainfuckProgram(listOf(*instructions))

fun loop(vararg instructions: BrainfuckProgram.Instruction) =
        Loop(listOf(*instructions))

class BrainfuckProgramTest {
    
    class InstructionTest {
    
        class IncrementTest {
            @Test fun testExecute() {
                val state = BrainfuckProgram.State(byteArrayOf(1, 0), 0)
                val context = BrainfuckInterpreter.State(state, EmptyInputStream, EmptyOutputStream)
                Inc.execute(context)
                assertArrayEquals(byteArrayOf(2, 0), context.state.cells)
            }
        }
    
        class DecrementTest {
            @Test fun testExecute() {
                val state = BrainfuckProgram.State(byteArrayOf(1, 0), 0)
                val context = BrainfuckInterpreter.State(state, EmptyInputStream, EmptyOutputStream)
                Dec.execute(context)
                assertArrayEquals(byteArrayOf(0, 0), context.state.cells)
            }
        }
    
        class NextCellTest {
            @Test fun testExecute() {
                val state = BrainfuckProgram.State(byteArrayOf(1, 0), 0)
                val context = BrainfuckInterpreter.State(state, EmptyInputStream, EmptyOutputStream)
                Next.execute(context)
                assertEquals(0, context.state.currentCell)
            }
        }
    
        class PreviousCellTest {
            @Test fun testExecute() {
                val state = BrainfuckProgram.State(byteArrayOf(1, 0), 1)
                val context = BrainfuckInterpreter.State(state, EmptyInputStream, EmptyOutputStream)
                Prev.execute(context)
                assertEquals(1, context.state.currentCell)
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
                val state = BrainfuckProgram.State(byteArrayOf(iterations, 0), 0)
                val context = BrainfuckInterpreter.State(state, EmptyInputStream, output)
                loop(Out, Dec).execute(context)
                assertEquals(iterations, output.iterations)
            }
        
            @Test fun testExecuteNoIterations() {
                val output = object : OutputStream() {
                    override fun write(b: Int) = fail()
                }
                val state = BrainfuckProgram.State(byteArrayOf(1, 0), 1)
                val context = BrainfuckInterpreter.State(state, EmptyInputStream, output)
                loop(Out).execute(context)
            }
        }
    
        class WriteTest {
            @Test fun testExecute() {
                val value = 7.toByte()
                val output = ByteArrayOutputStream()
                val state = BrainfuckProgram.State(byteArrayOf(value, 0), 0)
                val context = BrainfuckInterpreter.State(state, EmptyInputStream, output)
                Out.execute(context)
                assertArrayEquals(byteArrayOf(value), output.toByteArray())
            }
        }
    
        class ReadTest {
            @Test fun testExecute() {
                val value = 7.toByte()
                val input = ByteArrayInputStream(byteArrayOf(value))
                val state = BrainfuckProgram.State(byteArrayOf(1, 0), 0)
                val context = BrainfuckInterpreter.State(state, input, EmptyOutputStream)
                In.execute(context)
                assertArrayEquals(byteArrayOf(value, 0), context.state.cells)
            }
        
            @Test fun testExecuteEmptyStream() {
                val state = BrainfuckProgram.State(byteArrayOf(1, 0), 0)
                val context = BrainfuckInterpreter.State(state, EmptyInputStream, EmptyOutputStream)
                In.execute(context)
                assertArrayEquals(byteArrayOf(0, 0), context.state.cells)
            }
        }
    
    }
    
}