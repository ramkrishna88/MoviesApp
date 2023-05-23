package com.example.moviesapp.data.repository.movierepository

interface MoviesDataSource {
    suspend fun getNowPlayingMovies(page: Int): String
}