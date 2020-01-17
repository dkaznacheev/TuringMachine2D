package ru.hse.tm2d.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ru.hse.tm2d.model.Alphabet
import ru.hse.tm2d.model.Cell
import ru.hse.tm2d.model.TMMap2D
import ru.hse.tm2d.model.TuringMachine2D

class TMFieldView: View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    lateinit var field: TMMap2D
    lateinit var tm: TuringMachine2D

    private val headPaint = Paint()
    private val gridPaint = Paint()
    private val cellPaint = Paint()

    init {
        initPaints()
    }

    private fun initPaints() {
        gridPaint.color = Color.LTGRAY

        headPaint.style = Paint.Style.STROKE
        headPaint.color = Color.BLACK
        headPaint.strokeWidth = 5f

        cellPaint.style = Paint.Style.FILL
        cellPaint.color = Color.BLACK
        cellPaint.textSize = CELL_SIZE / 4f * 3
        cellPaint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val centerX = width / 2 - CELL_SIZE / 2
        val centerY = height / 2 - CELL_SIZE / 2
        drawGrid(canvas, centerX, centerY)

        val cellsX = width / CELL_SIZE
        val cellsY = height / CELL_SIZE
        val cells = field.rectangleIterator(
            tm.headX - cellsX / 2 - 2,
            tm.headY - cellsY / 2 - 2,
            tm.headX + cellsX / 2 + 2,
            tm.headY + cellsY / 2 + 2)

        drawCells(canvas, cells, centerX, centerY)

        drawHead(canvas, centerX.toFloat(), centerY.toFloat())
    }

    private fun drawHead(canvas: Canvas?, centerX: Float, centerY: Float) {
        canvas?.drawRect(centerX, centerY, centerX + CELL_SIZE, centerY + CELL_SIZE, headPaint)
    }

    private fun drawCells(canvas: Canvas?, cells: Iterator<Cell<Alphabet>>, centerX: Int, centerY: Int) {

        for (cell in cells) {
            val cellX = ((cell.x - tm.headX) * CELL_SIZE + centerX + CELL_SIZE / 2).toFloat()
            val cellY = (cell.y - tm.headY) * CELL_SIZE + centerY +
                    CELL_SIZE / 2 - cellPaint.ascent() / 2.5f
            when (cell.value) {
                Alphabet.ONE -> canvas?.drawText("1", cellX, cellY, cellPaint)
                Alphabet.ZERO -> canvas?.drawText("0", cellX, cellY, cellPaint)
                else -> {}
            }
        }
    }

    private fun drawGrid(canvas: Canvas?, centerX: Int, centerY: Int) {
        for (i in centerX downTo 0 step CELL_SIZE) {
            canvas?.drawLine(i.toFloat(), 0f, i.toFloat(), height.toFloat(), gridPaint)
        }
        for (i in centerX..width step CELL_SIZE) {
            canvas?.drawLine(i.toFloat(), 0f, i.toFloat(), height.toFloat(), gridPaint)
        }

        for (i in centerY downTo 0 step CELL_SIZE) {
            canvas?.drawLine(0f, i.toFloat(), width.toFloat(), i.toFloat(), gridPaint)
        }
        for (i in centerY..height step CELL_SIZE) {
            canvas?.drawLine(0f, i.toFloat(), width.toFloat(), i.toFloat(), gridPaint)
        }
    }

    companion object {
        private const val CELL_SIZE = 100
    }
}