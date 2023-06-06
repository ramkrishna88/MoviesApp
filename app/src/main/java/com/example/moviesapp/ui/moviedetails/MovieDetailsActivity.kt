package com.example.moviesapp.ui.moviedetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieTitle = intent.getStringExtra("movieTitle")
        val posterPath = intent.getStringExtra("posterPath")
        val overview = intent.getStringExtra("overview")
        val releaseDate = intent.getStringExtra("release_date")
        val originalLanguage = intent.getStringExtra("original_language")

        binding.movieDetailsTitle.text = movieTitle
        Glide.with(applicationContext)
            .load("https://image.tmdb.org/t/p/w500$posterPath")
            .into(binding.movieDetailsPoster)

        binding.movieDetailsReleaseDateResponse.text = releaseDate
        binding.movieDetailsOriginalLanguageResponse.text = originalLanguage
        binding.movieDetailsOverviewResponse.text = overview
    }
}