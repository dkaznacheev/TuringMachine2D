package ru.hse.tm2d.model

sealed class Instruction
object Left: Instruction()
object Right: Instruction()
object Up: Instruction()
object Down: Instruction()
data class Write(val value: TMAlphabet): Instruction()
object Return: Instruction()
data class Goto(val label: Int): Instruction()
data class If(val value: TMAlphabet, val label: Int): Instruction()

typealias TM2DProgram = List<Instruction>