package com.example.moviesapp.data.repository.movierepository

import com.example.moviesapp.data.api.APIService

class MoviesDataSourceImpl(private val apiService: APIService) : MoviesDataSource {
    override suspend fun getNowPlayingMovies(page: Int): String {
        return apiService.getNowPlayingMovies(page)
    }
}