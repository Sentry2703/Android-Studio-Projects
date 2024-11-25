package com.example.randomapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.randomapp.DatabaseImpl.PlayThrough
import com.example.randomapp.DatabaseImpl.PlayThroughDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SuccessActivity: AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val playerDisplay: TextView = findViewById<TextView>(R.id.userName)
        val winningDisplay: TextView = findViewById(R.id.winPrefix)
        val playButton: Button = findViewById<Button>(R.id.playButton)
        val saveButton: Button = findViewById<Button>(R.id.saveButton)
        val leaderboardButton: Button = findViewById(R.id.leaderboardButton)

        val playThrough: PlayThrough? = intent.getParcelableExtra("playThrough")
        playerDisplay.text = playThrough?.name

        val database = Room.databaseBuilder(
            applicationContext,
            PlayThroughDatabase::class.java,
            "playThrough"
        ).fallbackToDestructiveMigration().build()

        if (playThrough != null) {
            playThrough.weightedScore = calculateWeight(playThrough.pairsAttempted, playThrough.guessesUsed)
            val result: String = "${winningDisplay.text} ${String.format("%.2f", playThrough.weightedScore)}"
            winningDisplay.text = result
        } else {
            winningDisplay.text = "Sorry, something went wrong with the game."
        }

        saveButton.setOnClickListener {
            if (playThrough != null) {
                addNewRecord(database, playThrough)
            }
            Toast.makeText(this, "Saving...", Toast.LENGTH_SHORT).show()
            it.visibility = View.GONE
        }

        playButton.setOnClickListener {
            Toast.makeText(this, "Setting up Field Again", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun calculateWeight(pairsAttempted: Int, guessesUsed: Int): Double {
        if (guessesUsed == 0) {
            return 0.0
        }

        return (pairsAttempted * pairsAttempted) * (10.0 / guessesUsed)
    }

    private fun addNewRecord(database: PlayThroughDatabase, record: PlayThrough) {
        CoroutineScope(Dispatchers.IO).launch {
            database.playThroughDao().insert(record)
        }
    }

}