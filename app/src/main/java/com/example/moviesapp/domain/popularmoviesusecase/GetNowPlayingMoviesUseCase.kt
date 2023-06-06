package com.example.moviesapp.domain.popularmoviesusecase

import com.example.moviesapp.data.api.APIService

class GetNowPlayingMoviesUseCase(private val apiService: APIService) {
    suspend operator fun invoke(page: Int): String {
        return apiService.getNowPlayingMovies(page)
    }
}