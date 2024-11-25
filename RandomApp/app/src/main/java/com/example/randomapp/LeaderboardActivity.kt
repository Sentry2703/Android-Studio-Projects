package com.example.randomapp

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginEnd
import androidx.core.view.setPadding
import androidx.room.Room
import com.example.randomapp.DatabaseImpl.PlayThrough
import com.example.randomapp.DatabaseImpl.PlayThroughDatabase
import com.facebook.stetho.inspector.database.ContentProviderSchema.Table
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LeaderboardActivity: AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leaderboard_entry)

        val leaderboardTable = findViewById<TableLayout>(R.id.leaderTable)

        val headerRow = fillUpRow(
            arrayOf("Player Rank", "Player Name", "# Cards", "Score"),
            true
        )

        leaderboardTable.addView(headerRow)

        val playDatabase = Room.databaseBuilder(
            applicationContext,
            PlayThroughDatabase::class.java,
            "playThrough"
        ).fallbackToDestructiveMigration().build()

        CoroutineScope(Dispatchers.Main).launch {
            val topPlayers = getTopPlays(playDatabase, 10)
            if (topPlayers.isNotEmpty()) {
                for (player in topPlayers.indices) {
                    val playThrough = topPlayers[player]
                    val tableRow = fillUpRow(
                        arrayOf("${player + 1}", playThrough.name, "${playThrough.pairsAttempted}", String.format("%.2f", playThrough.weightedScore)),
                        false
                    )
                    leaderboardTable.addView(tableRow)
                }
            }
        }
    }

    private fun fillUpRow(data: Array<String>, isHeader: Boolean): TableRow {

        val row = TableRow(this)
        row.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        for (text in data) {
            val textView = TextView(this)
            textView.layoutParams = TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
            )
            textView.text = text
            textView.gravity = Gravity.CENTER
            textView.setPadding(8, 8, 8, 8)
            textView.setBackgroundResource(R.drawable.cell_border)

            if (isHeader) {
                textView.setTypeface(null, Typeface.BOLD) // Bold text for headers
            }

            row.addView(textView)
        }


        return row
    }

    private suspend fun getTopPlays(playDatabase: PlayThroughDatabase, n: Int) :List<PlayThrough> {
        return withContext(Dispatchers.IO) {
            playDatabase.playThroughDao().getTopPlayThrough(n)
        }
    }
}