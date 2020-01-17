package ru.hse.tm2d

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.hse.tm2d.model.*

class MainActivity : AppCompatActivity() {
    private var ready = true

    private var field = TMMap2D()
    private val program = listOf(
        mapOf(Alphabet.BLANK to Action(Alphabet.ONE, Direction.UP, 0),
              Alphabet.ZERO to Action(Alphabet.ONE, Direction.RIGHT, 1)),
        mapOf(Alphabet.BLANK to Action(Alphabet.ONE, Direction.RIGHT, 1),
            Alphabet.ZERO to Action(Alphabet.ONE, Direction.DOWN, 2)),
        mapOf(Alphabet.BLANK to Action(Alphabet.ONE, Direction.DOWN, 2),
            Alphabet.ZERO to Action(Alphabet.ONE, Direction.LEFT, 3)),
        mapOf(Alphabet.BLANK to Action(Alphabet.ONE, Direction.LEFT, 3),
            Alphabet.ZERO to Action(Alphabet.ONE, Direction.UP, 4)),
        mapOf(Alphabet.BLANK to Action(Alphabet.ONE, Direction.UP, 4),
            Alphabet.ONE to Action(Alphabet.ONE, Direction.NONE, TERMINAL_STATE))
    )
    private var tm = TuringMachine2D(program, field)

    private fun initTM() {
        field = TMMap2D()
        field[0, -2] = Alphabet.ZERO
        field[4, -2] = Alphabet.ZERO
        field[4, 2] = Alphabet.ZERO
        field[0, 2] = Alphabet.ZERO
        tm = TuringMachine2D(program, field)

        fieldView.field = field
        fieldView.tm = tm
    }

    private fun drawField() {
        fieldView.invalidate()
    }

    private fun runTMDemo() {
        if (ready) {
            ready = false
            button.text = "RUNNING"
            GlobalScope.launch(Dispatchers.Main) {
                initTM()
                while (!tm.isOver()) {
                    drawField()
                    delay(250L)
                    tm.step()
                }
                drawField()
                ready = true
                button.text = "RUN"
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTM()
        drawField()

        button.setOnClickListener { runTMDemo() }
    }
}
