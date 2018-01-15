package me.palazzomichi.brainfuckinterpreter.util

import me.palazzomichi.brainfuckinterpreter.stream.BrainfuckReader
import me.palazzomichi.brainfuckinterpreter.stream.BrainfuckWriter
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import java.io.Writer

/**
 * An empty [InputStream].
 */
object EmptyInputStream : InputStream() {
    override fun read() = -1
    override fun reset() = Unit
    override fun markSupported() = true
}

/**
 * An [OutputStream] that does nothing.
 */
object EmptyOutputStream : OutputStream() {
    override fun write(b: Int) = Unit
}

/** Creates a new [BrainfuckReader] for the [Reader]. */
fun Reader.brainfuck() = BrainfuckReader(this)

/** Creates a new [BrainfuckWriter] for the [Writer]. */
fun Writer.brainfuck() = BrainfuckWriter(this)
