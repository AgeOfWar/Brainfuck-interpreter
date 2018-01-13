package me.palazzomichi.brainfuckinterpreter

import java.io.FileReader

fun main(args: Array<String>) {
    if (args.size != 1) {
        error("invalid arguments")
    }
    
    val path = args[0]
    val reader = FileReader(path)
    brainfuck.execute(reader, System.`in`, System.out)
    System.out.flush()
}
