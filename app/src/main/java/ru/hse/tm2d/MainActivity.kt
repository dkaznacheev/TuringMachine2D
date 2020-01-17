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
        mapOf(Alphabet.ZERO to Action(Alphabet.ONE, Direction.RIGHT, 0),
              Alphabet.ONE to Action(Alphabet.ONE, Direction.NONE, TERMINAL_STATE))
    )
    private var tm = TuringMachine2D(program, field)

    private fun drawFieldDemo() {
        var text = ""
        for (j in -2..2) {
            var s = ""
            for (i in 0..4) {
                s += if (tm.headX == i && tm.headY == j) "[" else " "
                s += when (field[i, j]) {
                    Alphabet.ZERO -> "0"
                    Alphabet.ONE -> "1"
                    else -> "_"
                }
                s += if (tm.headX == i && tm.headY == j) "]" else " "
            }
            text += s + "\n"
        }
        textView.text = text
    }

    private fun initTM() {
        field = TMMap2D()
        field[0, 0] = Alphabet.ZERO
        field[1, 0] = Alphabet.ZERO
        field[2, 0] = Alphabet.ONE
        tm = TuringMachine2D(program, field)
    }

    private fun runTMDemo() {
        if (ready) {
            ready = false
            button.text = "RUNNING"
            GlobalScope.launch(Dispatchers.Main) {
                initTM()
                while (!tm.isOver()) {
                    tm.step()
                    drawFieldDemo()
                    delay(500L)
                }
                ready = true
                button.text = "RUN"
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTM()
        drawFieldDemo()
        button.setOnClickListener { runTMDemo() }
    }
}
