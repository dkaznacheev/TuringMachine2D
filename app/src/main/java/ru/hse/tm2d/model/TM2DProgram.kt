package ru.hse.tm2d.model

enum class Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    NONE
}

const val TERMINAL_STATE = -1
data class Action(val write: Alphabet, val move: Direction, val newState: Int)

fun terminate(value: Alphabet): Action {
    return Action(value, Direction.NONE, TERMINAL_STATE)
}

typealias TM2DProgram = List<Map<Alphabet, Action>>