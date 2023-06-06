package com.example.moviesapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.databinding.ActivityHomeBinding
import com.example.moviesapp.ui.moviedetails.MovieDetailsActivity
import com.example.moviesapp.utils.UIState

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.moviesRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesAdapter = MoviesAdapter(emptyList()){movie->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("movieTitle", movie.title)
            intent.putExtra("posterPath", movie.posterPath)

            intent.putExtra("release_date", movie.releaseDate)
            intent.putExtra("original_language", movie.originalLanguage)
            intent.putExtra("overview", movie.overview)

            startActivity(intent)
        }
        binding.moviesRecyclerview.adapter = moviesAdapter
        viewModel.movies.observe(this) { movies ->
            moviesAdapter.setList(movies)
        }

        viewModel.uiState.observe(this) { uiState ->
            when (uiState) {
                is UIState.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UIState.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.moviesRecyclerview.visibility = View.VISIBLE
                }
                is UIState.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "UNABLE TO LOAD THE MOVIES", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getNowPlayingMovies(50)
    }
}