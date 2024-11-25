package com.example.randomapp.DatabaseImpl

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlayThrough::class], version = 2)
abstract class PlayThroughDatabase: RoomDatabase() {
    abstract fun playThroughDao(): PlayThroughDao
}