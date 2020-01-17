package ru.hse.tm2d.model

class TMMap2D(): Field2D<Alphabet> {
    private val map = HashMap<Pair<Int, Int>, Alphabet>()

    constructor(values: List<Cell<Alphabet>>) : this() {
        for (cell in values) {
            map[cell.x to cell.y] = cell.value
        }
    }

    override fun set(x: Int, y: Int, value: Alphabet) {
        if (value == Alphabet.BLANK) {
            map.remove(x to y)
        } else {
            map[x to y] = value
        }
    }

    override fun get(x: Int, y: Int): Alphabet {
        return map[x to y] ?: Alphabet.BLANK
    }

    override fun rectangleIterator(startX: Int, startY: Int, endX: Int, endY: Int): Iterator<Cell<Alphabet>> {
        return object : Iterator<Cell<Alphabet>> {
            var x = startX
            var y = startY

            override fun hasNext(): Boolean {
                while (get(x, y) == Alphabet.BLANK) {
                    x++
                    if (x >= endX) {
                        x = startX
                        y++
                    }
                    if (y >= endY)
                        return false
                }
                return true
            }

            override fun next(): Cell<Alphabet> {
                if (!hasNext())
                    throw NoSuchElementException()
                val result = Cell(x, y, get(x, y))
                x++
                if (x >= endX) {
                    x = startX
                    y++
                }
                return result
            }
        }
    }
}