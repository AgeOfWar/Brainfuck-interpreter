package me.palazzomichi.brainfuckinterpreter.util

import me.palazzomichi.brainfuckinterpreter.stream.BrainfuckReader
import me.palazzomichi.brainfuckinterpreter.stream.BrainfuckWriter
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import java.io.Writer

object EmptyInputStream : InputStream() {
    override fun read() = -1
    override fun reset() = Unit
    override fun markSupported() = true
}

object EmptyOutputStream : OutputStream() {
    override fun write(b: Int) = Unit
}

fun Reader.brainfuck() = BrainfuckReader(this)

fun Writer.brainfuck() = BrainfuckWriter(this)
