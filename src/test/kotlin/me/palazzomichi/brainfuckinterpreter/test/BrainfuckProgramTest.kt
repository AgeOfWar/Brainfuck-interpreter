package me.palazzomichi.brainfuckinterpreter.test

import me.palazzomichi.brainfuckinterpreter.BrainfuckInterpreter
import me.palazzomichi.brainfuckinterpreter.BrainfuckProgram
import me.palazzomichi.brainfuckinterpreter.cellIterator
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
                val cells = byteArrayOf(1, 0)
                val config = BrainfuckInterpreter.Configuration(EmptyInputStream, EmptyOutputStream)
                Inc.execute(cells.cellIterator(), config)
                assertArrayEquals(byteArrayOf(2, 0), cells)
            }
        }
    
        class DecrementTest {
            @Test fun testExecute() {
                val cells = byteArrayOf(1, 0)
                val config = BrainfuckInterpreter.Configuration(EmptyInputStream, EmptyOutputStream)
                Dec.execute(cells.cellIterator(), config)
                assertArrayEquals(byteArrayOf(0, 0), cells)
            }
        }
    
        class NextCellTest {
            @Test fun testExecute() {
                val cells = byteArrayOf(1, 0).cellIterator()
                val config = BrainfuckInterpreter.Configuration(EmptyInputStream, EmptyOutputStream)
                Next.execute(cells, config)
                assertEquals(0, cells.current)
            }
        }
    
        class PreviousCellTest {
            @Test fun testExecute() {
                val cells = byteArrayOf(1, 0).cellIterator(1)
                val config = BrainfuckInterpreter.Configuration(EmptyInputStream, EmptyOutputStream)
                Prev.execute(cells, config)
                assertEquals(1, cells.current)
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
                val state = byteArrayOf(iterations, 0)
                val cells = state.cellIterator()
                val config = BrainfuckInterpreter.Configuration(EmptyInputStream, output)
                loop(Out, Dec).execute(cells, config)
                assertEquals(iterations, output.iterations)
            }
        
            @Test fun testExecuteNoIterations() {
                val output = object : OutputStream() {
                    override fun write(b: Int) = fail()
                }
                val cells = byteArrayOf(1, 0).cellIterator(1)
                val config = BrainfuckInterpreter.Configuration(EmptyInputStream, output)
                loop(Out).execute(cells, config)
            }
        }
    
        class WriteTest {
            @Test fun testExecute() {
                val value = 7.toByte()
                val output = ByteArrayOutputStream()
                val cells = byteArrayOf(value, 0)
                val config = BrainfuckInterpreter.Configuration(EmptyInputStream, output)
                Out.execute(cells.cellIterator(), config)
                assertArrayEquals(byteArrayOf(value), output.toByteArray())
            }
        }
    
        class ReadTest {
            @Test fun testExecute() {
                val value = 7.toByte()
                val input = ByteArrayInputStream(byteArrayOf(value))
                val cells = byteArrayOf(1, 0)
                val config = BrainfuckInterpreter.Configuration(input, EmptyOutputStream)
                In.execute(cells.cellIterator(), config)
                assertArrayEquals(byteArrayOf(value, 0), cells)
            }
        
            @Test fun testExecuteEmptyStream() {
                val cells = byteArrayOf(1, 0)
                val config = BrainfuckInterpreter.Configuration(EmptyInputStream, EmptyOutputStream)
                In.execute(cells.cellIterator(), config)
                assertArrayEquals(byteArrayOf(0, 0), cells)
            }
        }
    
    }
    
}