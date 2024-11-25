package com.example.randomapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.randomapp.CardImpl.Card
import com.example.randomapp.CardImpl.CardAdapter
import com.example.randomapp.DatabaseImpl.PlayThrough

class GameActivity : AppCompatActivity() {

    private var cards: ArrayList<Card> = ArrayList()
    private val TAG = "GameActivity"
    private val matching: ArrayList<Card> = ArrayList()
    private lateinit var cardAdapter: CardAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Safely handle intent data
        intent.getStringArrayListExtra("DataList")?.let { dataList ->
            Card.fromArray(dataList).let { cards.addAll(it) }
        }

        val attemptCounter: TextView = findViewById<TextView>(R.id.attempts)
        val recyclerView: RecyclerView = findViewById(R.id.cardRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        // Initialize adapter
        cardAdapter = CardAdapter(cards) { card ->
            handleCardClick(card, recyclerView, attemptCounter)
        }

        recyclerView.adapter = cardAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun handleCardClick(card: Card, recyclerView: RecyclerView, attemptCounter: TextView) {
        when (card.isOpen()) {
            true -> {
                card.doClose()
                matching.remove(card)
            }
            false -> {
                matching.add(card)
                card.doOpen()
            }
        }

        // If two cards are selected, check for a match after a short delay
        if (matching.size == 2) {
            attemptCounter.text = "${Integer.parseInt(attemptCounter.text.toString()) + 1}"
            recyclerView.postDelayed({
                checkForMatch(attemptCounter)
            }, 500)  // 1/2 second delay for user to see the cards
        }

        cardAdapter.notifyItemChanged(cards.indexOf(card)) // Update the UI for the selected card
    }

    private fun checkForMatch(attemptCounter: TextView) {
        if (matching[0].matchesButNotSame(matching[1])) {
            // Cards matched
            Toast.makeText(this, "${matching[1].getName()} matched!", Toast.LENGTH_SHORT).show()

            val indicesToRemove = matching.map { cards.indexOf(it) }.sortedDescending()

            // Remove matched cards and notify the adapter
            indicesToRemove.forEach { index ->
                cards.removeAt(index)
                cardAdapter.notifyItemRemoved(index)
            }
        } else {
            // Cards do not match, close them
            matching.forEach { it.doClose() }
            Toast.makeText(this, "Wrong match!", Toast.LENGTH_SHORT).show()

            matching.forEach { card ->
                cardAdapter.notifyItemChanged(cards.indexOf(card))
            }
        }

        // Clear the matching list
        matching.clear()

        // Check if the game is over (all cards matched)
        if (cards.isEmpty()) {
            val playThrough: PlayThrough? = intent.getParcelableExtra("playThrough")
            if (playThrough != null) {
                playThrough.guessesUsed = Integer.parseInt(attemptCounter.text.toString())
            }
            val intent = Intent(this, SuccessActivity::class.java)
            intent.putExtra("playThrough", playThrough)
            startActivity(intent)
        }
    }
}
