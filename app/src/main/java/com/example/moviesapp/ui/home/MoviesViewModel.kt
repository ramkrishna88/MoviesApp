package com.example.moviesapp.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.api.APIService
import com.example.moviesapp.data.model.movies.Movie
import com.example.moviesapp.data.model.movies.MovieResponse
import com.example.moviesapp.data.repository.movierepository.MoviesDataSourceImpl
import com.example.moviesapp.data.repository.movierepository.MoviesRepository
import com.example.moviesapp.data.repository.movierepository.MoviesRepositoryImpl
import com.example.moviesapp.domain.popularmoviesusecase.GetNowPlayingMoviesUseCase
import com.example.moviesapp.utils.UIState
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


class MoviesViewModel : ViewModel() {

    private var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase? = null
    private var repository: MoviesRepository? = null

    private val apiService: APIService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(APIService::class.java)

    init {
        val dataSource = MoviesDataSourceImpl(apiService)
        repository = MoviesRepositoryImpl(dataSource)
        getNowPlayingMoviesUseCase = repository?.let { GetNowPlayingMoviesUseCase(it) }
    }

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
                    val response = getNowPlayingMoviesUseCase?.invoke(page)
                    val parsedResponse = parseResponse(response)
                    movieList.addAll(parsedResponse.results)
                }
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
               // listOf(movie)
            movie
            } catch (e: JsonSyntaxException) {
            MovieResponse(emptyList())
        }
    }
}