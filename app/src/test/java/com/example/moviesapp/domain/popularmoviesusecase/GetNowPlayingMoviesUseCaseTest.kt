package com.example.moviesapp.domain.popularmoviesusecase

import com.example.moviesapp.data.api.APIService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetNowPlayingMoviesUseCaseTest {

    @Mock
    private lateinit var mockApiService: APIService
    private lateinit var useCase: GetNowPlayingMoviesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = GetNowPlayingMoviesUseCase(mockApiService)
    }

    @Test
    fun `invoke should return expected result`() {
        runBlocking {
            val page = 1
            val expectedResult = "Mocked response"
            `when`(mockApiService.getNowPlayingMovies(page)).thenReturn(expectedResult)
            val result = useCase(page)
            assertEquals(expectedResult, result)
        }
    }
}