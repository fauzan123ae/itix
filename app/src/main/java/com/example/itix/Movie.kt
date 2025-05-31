package com.example.itix

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val imageRes: Int,
    val category: String,
    val genre: String,
    val duration: String,
    val isFavorite: Boolean = false
)
