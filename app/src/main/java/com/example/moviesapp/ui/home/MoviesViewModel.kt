package com.example.moviesapp.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.model.database.MovieLocal
import com.example.moviesapp.data.model.movies.MovieDto
import com.example.moviesapp.data.model.movies.MoviesResponse
import com.example.moviesapp.data.repository.movierepository.MoviesRepositoryDatabase
import com.example.moviesapp.domain.popularmoviesusecase.GetNowPlayingMoviesUseCase
import com.example.moviesapp.utils.UIState
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val moviesRepositoryDatabase: MoviesRepositoryDatabase
) : ViewModel() {

    private val _movies = MutableLiveData<List<MovieDto>>()
    val movies: LiveData<List<MovieDto>> = _movies

    private val _uiState = MutableLiveData<UIState<Any>>()
    val uiState: LiveData<UIState<Any>> get() = _uiState
    val moviesDatabase: Flow<List<MovieLocal>> get() = moviesRepositoryDatabase.getAllMovies()

    fun getNowPlayingMovies(page: Int, totalPages: Int) {
        val pageList = (1..totalPages).toList()
        if (page in pageList) {
            viewModelScope.launch {
                _uiState.postValue(UIState.LOADING(true))
                try {
                    val movieDtoList = mutableListOf<MovieDto>()
                    val response = getNowPlayingMoviesUseCase.invoke(page)
                    val parsedResponse = parseResponse(response)
                    movieDtoList.addAll(parsedResponse.results)

                    moviesRepositoryDatabase.insertMovies(movieDtoList.map { it.toMovieLocal() })
                    _uiState.postValue(UIState.SUCCESS(moviesDatabase))
                } catch (e: Exception) {
                    _uiState.postValue(UIState.ERROR(e.message))
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun parseResponse(response: String?): MoviesResponse {
        return try {
            val newResponse = Gson()
            val movie = newResponse.fromJson(response, MoviesResponse::class.java)
            movie
        } catch (e: JsonSyntaxException) {
            MoviesResponse(emptyList())
        }
    }

    private fun MovieDto.toMovieLocal(): MovieLocal {
        return MovieLocal(
            id = id,
            title = title,
            overview = overview,
            posterPath = posterPath,
            releaseDate = releaseDate,
            originalLanguage = originalLanguage
        )
    }
}