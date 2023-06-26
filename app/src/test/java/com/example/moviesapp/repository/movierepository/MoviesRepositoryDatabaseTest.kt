package com.example.moviesapp.repository.movierepository

import com.example.moviesapp.data.model.database.MovieDao
import com.example.moviesapp.data.model.database.MovieLocal
import com.example.moviesapp.data.repository.movierepository.MoviesRepositoryDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MoviesRepositoryDatabaseTest {

    @Mock
    private lateinit var mockMovieDao: MovieDao
    private lateinit var repository: MoviesRepositoryDatabase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = MoviesRepositoryDatabase(mockMovieDao)
    }

    @Test
    fun `getAllMovies should return expected result`() = runBlockingTest {
        val expectedMovies = listOf(
            MovieLocal(1, "Movie 1", "Overview 1", "posterPath1", "2023-01-01", "en"),
            MovieLocal(2, "Movie 2", "Overview 2", "posterPath2", "2023-02-02", "en")
        )
        `when`(mockMovieDao.getAllMovies()).thenReturn(flowOf(expectedMovies))
        val result = repository.getAllMovies().first()
        assertEquals(expectedMovies, result)
    }

    @Test
    fun `insertMovies should call movieDao insert`() = runBlockingTest {
        val movieLocals = listOf(
            MovieLocal(1, "Movie 1", "Overview 1", "posterPath1", "2023-01-01", "en"),
            MovieLocal(2, "Movie 2", "Overview 2", "posterPath2", "2023-02-02", "en")
        )
        repository.insertMovies(movieLocals)
        verify(mockMovieDao).insert(movieLocals)
    }
}