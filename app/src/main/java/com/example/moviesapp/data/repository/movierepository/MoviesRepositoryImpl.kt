package com.example.moviesapp.data.repository.movierepository

class MoviesRepositoryImpl(private val dataSource: MoviesDataSource) : MoviesRepository {
    override suspend fun getNowPlayingMovies(page: Int): String {
        return dataSource.getNowPlayingMovies(page)
    }
}