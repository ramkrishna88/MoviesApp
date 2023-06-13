package com.example.moviesapp.data.repository.movierepository

import com.example.moviesapp.data.model.database.MovieDao
import com.example.moviesapp.data.model.database.MovieLocal
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryDatabase(private val movieDao: MovieDao) {

    fun getAllMovies(): Flow<List<MovieLocal>> {
        return movieDao.getAllMovies()
    }
    suspend fun insertMovies(movieLocals: List<MovieLocal>) {
        movieDao.insert(movieLocals)
    }
}