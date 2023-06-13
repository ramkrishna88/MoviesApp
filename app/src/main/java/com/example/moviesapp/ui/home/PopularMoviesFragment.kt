package com.example.moviesapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentPopularMoviesBinding
import com.example.moviesapp.utils.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private lateinit var binding: FragmentPopularMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularMoviesBinding.inflate(layoutInflater)
        binding.moviesRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        moviesAdapter = MoviesAdapter(emptyList(), { movie ->
            findNavController().navigate(
                R.id.action_popularMoviesFragment_to_popularMoviesDetailsFragment,
                bundleOf("movieItem" to movie)
            )
        }, {
            currentPage++
            viewModel.getNowPlayingMovies(currentPage, 50)
        })

    binding.moviesRecyclerview.adapter = moviesAdapter
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            moviesAdapter.setList(movies)
        }

        viewModel.moviesDatabase.observe(viewLifecycleOwner) { databaseMovies ->
            moviesAdapter.setDatabaseMovieList(databaseMovies)
        }

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
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
                    Toast.makeText(requireContext(), "UNABLE TO LOAD THE MOVIES", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }
}

fun <T> Flow<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) {
    owner.lifecycleScope.launchWhenStarted {
        collect { value ->
            observer(value)
        }
    }
}