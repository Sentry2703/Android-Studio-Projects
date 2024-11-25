package com.example.randomapp.CardImpl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.randomapp.R

class CardAdapter(private val cards: List<Card>, private val onCardClick: (Card) -> Unit):
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.textViewCard)
        private val imageView: ImageView = itemView.findViewById(R.id.imageLogo)

        // Bind data to the card views
        fun bind(card: Card) {
            textView.text = card.getName()

            // Set visibility based on whether the card is open
            if (card.isOpen()) {
                imageView.visibility = View.INVISIBLE
                textView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.VISIBLE
                textView.visibility = View.INVISIBLE
            }

            // Handle card click
            itemView.setOnClickListener {
                onCardClick(card) // Pass the card to the click handler
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position]) // Bind each card data to the view
    }

    override fun getItemCount(): Int = cards.size
}
