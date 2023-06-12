package com.example.moviesapp.data.model.movies

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    val results: List<MovieDto>
)