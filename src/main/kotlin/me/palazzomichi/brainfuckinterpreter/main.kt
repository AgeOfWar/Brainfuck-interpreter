package me.palazzomichi.brainfuckinterpreter

import me.palazzomichi.brainfuckinterpreter.util.execute
import java.io.FileReader

fun main(args: Array<String>) {
    if (args.size != 1) {
        error("invalid arguments")
    }
    
    val path = args[0]
    val reader = FileReader(path)
    val brainfuck = BrainfuckInterpreter(System.`in`, System.out)
    brainfuck.execute(reader)
    System.out.flush()
}
