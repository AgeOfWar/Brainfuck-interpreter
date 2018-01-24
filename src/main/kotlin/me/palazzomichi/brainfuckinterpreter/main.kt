package me.palazzomichi.brainfuckinterpreter

import me.palazzomichi.brainfuckinterpreter.util.execute
import java.io.FileReader

fun main(args: Array<String>) = when (args.size) {
    1 -> main(args[0], "-f")
    2 -> main(args[1], args[0])
    else -> main()
}

fun main() {
    println("""
        Usage: [-fsi] <program>
        -f --file       run brainfuck file
        -s --string     run code directly
        -i --infinite   run without cell limits
        """.trimIndent())
}

fun main(arg: String, option: String) = when (option) {
    "-f", "--file" -> main(FileReader(arg))
    "-s", "--string" -> main(arg)
    "-i", "--infinite",
    "-fi", "-if" -> main(FileReader(arg), CellIterator.infinite())
    "-si", "-is" -> main(arg, CellIterator.infinite())
    else -> main()
}

fun main(program: FileReader, cellIterator: CellIterator = CellIterator.default()) {
    val brainfuck = BrainfuckInterpreter(System.`in`, System.out)
    brainfuck.execute(program, cellIterator)
    System.out.flush()
}

fun main(program: String, cellIterator: CellIterator = CellIterator.default()) {
    val brainfuck = BrainfuckInterpreter(System.`in`, System.out)
    brainfuck.execute(program, cellIterator)
    System.out.flush()
}
