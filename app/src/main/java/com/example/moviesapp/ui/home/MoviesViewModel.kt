package com.example.moviesapp.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.model.movies.Movie
import com.example.moviesapp.data.model.movies.MovieResponse
import com.example.moviesapp.domain.popularmoviesusecase.GetNowPlayingMoviesUseCase
import com.example.moviesapp.utils.UIState
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _uiState = MutableLiveData<UIState<Any>>()
    val uiState: LiveData<UIState<Any>> get() = _uiState

    fun getNowPlayingMovies(totalPages: Int) {
        val pageList = (1..totalPages).toList()
        viewModelScope.launch {
            _uiState.postValue(UIState.LOADING(true))
            try {
                val movieList = mutableListOf<Movie>()
                for (page in pageList) {
                    val response = getNowPlayingMoviesUseCase.invoke(page)
                    val parsedResponse = parseResponse(response)
                    movieList.addAll(parsedResponse.results)
                }
                movieList.shuffle()
                _movies.postValue(movieList)
                _uiState.postValue(UIState.SUCCESS(movieList))
            } catch (e: Exception) {
                _uiState.postValue(UIState.ERROR(e.message))
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun parseResponse(response: String?): MovieResponse {
        return try {
            val newResponse = Gson()
           val movie = newResponse.fromJson(response, MovieResponse::class.java)
            movie
            } catch (e: JsonSyntaxException) {
            MovieResponse(emptyList())
        }
    }
}