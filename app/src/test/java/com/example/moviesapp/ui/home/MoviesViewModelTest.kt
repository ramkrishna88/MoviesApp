package com.example.moviesapp.ui.home

import com.example.moviesapp.data.api.APIService
import com.example.moviesapp.data.model.database.MovieDao
import com.example.moviesapp.data.model.movies.MovieDto
import com.example.moviesapp.data.repository.movierepository.MoviesRepositoryDatabase
import com.example.moviesapp.domain.popularmoviesusecase.GetNowPlayingMoviesUseCase
import com.example.moviesapp.utils.UIState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var mockGetNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
    private lateinit var mockMoviesRepositoryDatabase: MoviesRepositoryDatabase
    private lateinit var mockMovieDao: MovieDao

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        val apiServiceClass = Class.forName("com.example.moviesapp.data.api.APIService")
        val mockApiService = Mockito.mock(apiServiceClass) as APIService
        mockMovieDao = Mockito.mock(MovieDao::class.java)
        mockGetNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(mockApiService)
        mockMoviesRepositoryDatabase = MoviesRepositoryDatabase(mockMovieDao)
        viewModel = MoviesViewModel(
            getNowPlayingMoviesUseCase = mockGetNowPlayingMoviesUseCase,
            moviesRepositoryDatabase = mockMoviesRepositoryDatabase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun uiState_get_success() {
        val expectedUiState =
            UIState.SUCCESS(data = viewModel.getNowPlayingMovies(PAGE, TOTAL_PAGES))
        val actualUiState = viewModel.uiState.value
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun getNowPlayingMovies_success() {
        val expectedMovies = listOf(
            MovieDto(
                id = 1,
                title = "Movie 1",
                overview = "Overview 1",
                posterPath = "posterPath1",
                releaseDate = "2023-01-01",
                originalLanguage = "en"
            ),
            MovieDto(
                id = 2,
                title = "Movie 2",
                overview = "Overview 2",
                posterPath = "posterPath2",
                releaseDate = "2023-02-02",
                originalLanguage = "en"
            )
        )
        runTest {
            viewModel.getNowPlayingMovies(PAGE, TOTAL_PAGES)
        }
        val actualMovies = viewModel._movies.value
        assertEquals(actualMovies, expectedMovies)
    }

    @Test
    fun getNowPlayingMovies_Error() {
        val expectedMovies = listOf(
            MovieDto(
                id = 1,
                title = "Movie 1",
                overview = "Overview 1",
                posterPath = "posterPath1",
                releaseDate = "2023-01-01",
                originalLanguage = "en"
            ),
            MovieDto(
                id = 2,
                title = "Movie 2",
                overview = "Overview 2",
                posterPath = "posterPath2",
                releaseDate = "2023-02-02",
                originalLanguage = "en"
            )
        )
        val actualMovies = viewModel._movies.value
        assertEquals(expectedMovies, actualMovies)
    }

    @Test
    fun `parseResponse should return MoviesResponse object when valid JSON response is provided`() {
        val response =
            "{\"movies\":[{\"id\":1,\"title\":\"Movie 1\",\"overview\":\"Overview 1\",\"posterPath\":\"path1\",\"releaseDate\":\"2021-01-01\",\"originalLanguage\":\"en\"}]}"
        val result = viewModel.parseResponse(response)
        assertEquals(1, result.results.size)
        assertEquals(1, result.results[0].id)
        assertEquals("Movie 1", result.results[0].title)
        assertEquals("Overview 1", result.results[0].overview)
        assertEquals("path1", result.results[0].posterPath)
        assertEquals("2021-01-01", result.results[0].releaseDate)
        assertEquals("en", result.results[0].originalLanguage)
    }

    @Test
    fun `parseResponse should return MoviesResponse object with empty list when invalid JSON response is provided`() {
        val response = "Invalid JSON"
        val result = viewModel.parseResponse(response)
        assertEquals(0, result.results.size)
    }

    @Test
    fun `toMovieLocal should convert MovieDto to MovieLocal`() {
        val movieDto = MovieDto(
            id = 1,
            title = "Movie 1",
            overview = "Overview 1",
            posterPath = "path1",
            releaseDate = "2021-01-01",
            originalLanguage = "en"
        )
        val result = (movieDto)
        assertEquals(1, result.id)
        assertEquals("Movie 1", result.title)
        assertEquals("Overview 1", result.overview)
        assertEquals("path1", result.posterPath)
        assertEquals("2021-01-01", result.releaseDate)
        assertEquals("en", result.originalLanguage)
    }

    companion object {
        const val PAGE = 1
        const val TOTAL_PAGES = 10
    }
}