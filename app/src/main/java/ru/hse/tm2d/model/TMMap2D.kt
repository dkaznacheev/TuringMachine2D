package ru.hse.tm2d.model

class TMMap2D(): Field2D<TMAlphabet> {
    private val map = HashMap<Pair<Int, Int>, TMAlphabet>()

    constructor(values: List<Cell<TMAlphabet>>) : this() {
        for (cell in values) {
            map[cell.x to cell.y] = cell.value
        }
    }

    override fun set(x: Int, y: Int, value: TMAlphabet) {
        if (value == TMAlphabet.BLANK) {
            map.remove(x to y)
        } else {
            map[x to y] = value
        }
    }

    override fun get(x: Int, y: Int): TMAlphabet {
        return map[x to y] ?: TMAlphabet.BLANK
    }

    override fun rectangleIterator(startX: Int, startY: Int, endX: Int, endY: Int): Iterator<Cell<TMAlphabet>> {
        return object : Iterator<Cell<TMAlphabet>> {
            var x = startX
            var y = startY

            override fun hasNext(): Boolean {
                while (get(x, y) == TMAlphabet.BLANK) {
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

            override fun next(): Cell<TMAlphabet> {
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