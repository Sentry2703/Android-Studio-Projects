package com.lyft.memorymatch

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.R.drawable.*
import android.util.Log
import android.widget.ImageButton
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    private lateinit var grid: GridLayout
    private var images = listOf(
        star_on,
        ic_delete,
        arrow_up_float,
        menu_frame,
        ic_dialog_email,
        ic_menu_call,
        )
    private val buttons: ArrayList<ImageButton> = ArrayList()
    private val matching: ArrayList<ImageButton> = ArrayList()
    private var isAlreadyYellow: Boolean = false
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grid = findViewById(R.id.grid)

        setUpGrid()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setUpGrid() {
        for (i in 0 until 12) {
            val card = ImageButton(this)
            card.setImageResource(images[i/2])

            buttons.add(card)

            card.setOnClickListener {
                matching.add(card)

                if (matching.size == 1) {

                } else if (matching.size == 2) {
                    if (matching[0].drawable.constantState == matching[1].drawable.constantState) {
                        buttons[buttons.indexOf(matching[0])].setButtonColor(Color.GREEN)
                        buttons[buttons.indexOf(matching[1])].setButtonColor(Color.GREEN)

                        Log.i("MAin Activity", "Matching Images")
                    } else {
                        buttons[buttons.indexOf(matching[0])].setButtonColor(Color.BLUE)
                        buttons[buttons.indexOf(matching[1])].setButtonColor(Color.BLUE)
                        Log.i("MAin Activity", "Non Matching Images")
                    }
                    Log.i("MAin Activity", "${matching[0].drawable.toString()}")
                    Log.i("MAin Activity", "${matching[1].drawable.toString()}")
                }

                if (isAlreadyYellow) {
                    for (button in buttons) {
                        if (button.background.colorFilter != BlendModeColorFilter(Color.GREEN, BlendMode.MULTIPLY)) {
                            button.setButtonColor(Color.BLUE)
                        }
                    }
                    isAlreadyYellow = false
                } else {
                    if (card.background.colorFilter != BlendModeColorFilter(Color.GREEN, BlendMode.MULTIPLY)) {
                        card.setButtonColor(Color.YELLOW)
                    }
                    isAlreadyYellow = true
                }


            }
        }
        buttons.shuffle()

        for (button in buttons) {
            grid.addView(button)
            button.update()
        }
    }

    private fun ImageButton.update() {
        setButtonColor(DEACTIVATED_COLOR)

        val params = layoutParams as GridLayout.LayoutParams
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
    }

    private fun ImageButton.setButtonColor(@ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            background.colorFilter = BlendModeColorFilter(color, BlendMode.MULTIPLY)
        } else {
            background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        }
    }

    companion object {
        private const val DEACTIVATED_COLOR = Color.BLUE
    }
}
