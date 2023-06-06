package com.example.moviesapp.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.data.model.movies.Movie
import com.example.moviesapp.databinding.FragmentPopularMoviesDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPopularMoviesDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularMoviesDetailsBinding.inflate(layoutInflater)

        val movieItem = arguments?.getParcelable<Movie>("movieItem")
        movieItem?.let { movie ->
            movie.let {
                binding.movieDetailsTitle.text = it.title
                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500" + it.posterPath)
                    .error(R.drawable.ic_error)
                    .into(binding.movieDetailsPoster)

                binding.movieDetailsReleaseDateResponse.text = it.releaseDate
                binding.movieDetailsOriginalLanguageResponse.text = it.originalLanguage
                binding.movieDetailsOverviewResponse.text = it.overview
            }
        }
        return binding.root
    }
}

