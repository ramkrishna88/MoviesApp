package com.example.moviesapp.domain.popularmoviesusecase

import com.example.moviesapp.data.repository.movierepository.MoviesRepository

class GetNowPlayingMoviesUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(page: Int): String {
        return repository.getNowPlayingMovies(page)
    }
}