package me.palazzomichi.brainfuckinterpreter

import java.util.*

private const val STANDARD_CELL_NUMBER = 30_000

interface CellIterator {
    var current: Byte
    
    fun hasNext(): Boolean
    fun next()
    fun hasPrevious(): Boolean
    fun previous()
    
    companion object {
        fun default() = ByteArray(STANDARD_CELL_NUMBER).cellIterator()
        fun infinite() = LinkedList<Byte>().cellIterator()
    }
}

class CellArrayIterator(
        private val cells: ByteArray,
        private var pos: Int = 0
) : CellIterator {
    override var current: Byte
        get() = cells[pos]
        set(value) = cells.set(pos, value)
    
    override fun hasNext() = pos < cells.lastIndex
    
    override fun next() {
        pos++
    }
    
    override fun hasPrevious() = pos > 0
    
    override fun previous() {
        pos--
    }
}

class InfiniteCellIterator(
        private val cells: MutableList<Byte>,
        private var pos: Int = 0
) : CellIterator {
    override var current: Byte
        get() = cells[pos]
        set(value) {
            cells[pos] = value
        }
    
    override fun hasNext() = true
    
    override fun next() {
        pos++
        ensureCapacity()
    }
    
    override fun hasPrevious() = true
    
    override fun previous() {
        pos--
        ensureCapacity()
    }
    
    private fun ensureCapacity() {
        if (pos !in cells.indices) {
            pos = pos.coerceAtLeast(0)
            cells.add(pos, 0)
        }
    }
}

fun ByteArray.cellIterator(pos: Int = 0) = CellArrayIterator(this, pos)

fun MutableList<Byte>.cellIterator(pos: Int = 0) = InfiniteCellIterator(this, pos)
