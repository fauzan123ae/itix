package com.example.itix

import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE category = :category")
    suspend fun getMoviesByCategory(category: String): List<Movie>

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    suspend fun getFavorites(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Update
    suspend fun updateMovie(movie: Movie)
}
