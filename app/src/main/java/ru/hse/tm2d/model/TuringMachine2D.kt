package ru.hse.tm2d.model

import java.lang.Exception

enum class Alphabet {
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
    var state: Int = 0
        private set

    fun isOver(): Boolean {
        return state == TERMINAL_STATE
    }

    fun step() {
        if (isOver()) throw ProgramEndedException()
        val symbol = field[headX, headY]
        val action = program[state][symbol] ?: terminate(symbol)
        field[headX, headY] = action.write
        when (action.move) {
            Direction.LEFT -> headX--
            Direction.RIGHT -> headX++
            Direction.UP -> headY++
            Direction.DOWN -> headY--
            else -> {}
        }
        state = action.newState
    }
}