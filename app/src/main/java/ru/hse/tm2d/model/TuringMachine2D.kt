package ru.hse.tm2d.model

import java.lang.Exception

enum class TMAlphabet {
    ZERO,
    ONE,
    BLANK
}

class ProgramEndedException: Exception()

class TuringMachine2D(val program: TM2DProgram, val field: TMMap2D = TMMap2D()) {
    var headX: Int = 0
        private set
    var headY: Int = 0
        private set
    var instrPointer: Int = 0
        private set
    var isOver: Boolean = false
        private set

    fun step() {
        if (isOver) throw ProgramEndedException()
        when (val instr = program[instrPointer++]) {
            is Left -> headX--
            is Right -> headX++
            is Up -> headY++
            is Down -> headY--
            is Write -> field[headX, headY] = instr.value
            is Return -> isOver = true
            is Goto -> instrPointer = instr.label
            is If ->  {
                if (field[headX, headY] == instr.value) { instrPointer = instr.label }
            }
        }
        if (instrPointer >= program.size) {
            isOver = true
        }
    }
}