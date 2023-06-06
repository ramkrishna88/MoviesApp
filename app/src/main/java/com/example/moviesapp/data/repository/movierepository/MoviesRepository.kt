package com.example.moviesapp.data.repository.movierepository

interface MoviesRepository {
       suspend fun getNowPlayingMovies(page: Int): String
}