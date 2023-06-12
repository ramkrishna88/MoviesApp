package com.example.moviesapp.data.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieLocals: List<MovieLocal>)

    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<MovieLocal>>
}