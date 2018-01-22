package me.palazzomichi.brainfuckinterpreter

/**
 * Exception thrown when a problem occurs during the execution of a [BrainfuckProgram].
 *
 * @see BrainfuckInterpreter
 * @see BrainfuckProgram
 * @author Michi Palazzo
 */
class BrainfuckException(message: String, cause: Throwable? = null) : Exception(message, cause)
