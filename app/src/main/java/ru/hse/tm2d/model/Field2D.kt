package ru.hse.tm2d.model

data class Cell<T>(val x: Int, val y: Int, val value: T)

interface Field2D<T> {
    operator fun set(x: Int, y: Int, value: T)
    operator fun get(x: Int, y: Int): T
    fun rectangleIterator(startX: Int, startY: Int, endX: Int, endY: Int): Iterator<Cell<T>>
}