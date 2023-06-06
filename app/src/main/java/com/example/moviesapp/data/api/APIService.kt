package com.example.moviesapp.data.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService {

    companion object {
        const val API_KEY = "e196c4dc7f1c579d934c1e6444b36924"
    }

    @GET("movie/now_playing?api_key=$API_KEY")
    @Headers("Content-Type: application/json")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): String
}