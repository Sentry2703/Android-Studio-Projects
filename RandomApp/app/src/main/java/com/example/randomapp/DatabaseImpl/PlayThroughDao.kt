package com.example.randomapp.DatabaseImpl

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayThroughDao {
    @Query("SELECT * FROM PlayThrough")
    suspend fun getAll(): List<PlayThrough>

    @Query("SELECT * FROM PlayThrough ORDER BY WeightedScore DESC LIMIT :n")
    suspend fun getTopPlayThrough(n: Int): List<PlayThrough>

    @Insert
    suspend fun insert(record: PlayThrough)

    @Delete
    suspend fun delete(record: PlayThrough)
}