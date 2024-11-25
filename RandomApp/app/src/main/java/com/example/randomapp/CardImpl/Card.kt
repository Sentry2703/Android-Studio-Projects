package com.example.randomapp.CardImpl

import android.util.Log

class Card constructor (private var name: String?, private var open: Boolean = false, private var matched: Boolean = false){

    fun getName(): String = if (this.name != null) this.name!!.split(" ")[0] else "No Name"

    fun isOpen(): Boolean = this.open

    fun isMatched(): Boolean = this.matched

    fun doOpen(): Unit {
        this.open = true
    }

    fun doClose(): Unit {
        this.open = false
    }

    private fun successMatch(): Unit {
        this.matched = true
        doClose()
    }


    fun checkMatched(other: Card): Boolean {
        if (this.getName().split(" ")[0] == other.getName().split(" ")[0]) {
            this.successMatch()
            other.successMatch()
            return true
        }
        return false
    }

    fun matchesButNotSame(other: Card): Boolean {
        return this.checkMatched(other)
    }

    companion object {
        fun fromArray(normalArray: ArrayList<String>): ArrayList<Card> {
            val cards = ArrayList<Card>()

            for (i in 0 until normalArray.size) {
                val c = Card(normalArray[i])
                cards.add(c)
            }
            Log.d("Array", "${cards.size}")

            return cards
        }
    }
}