package com.example.moviesapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.data.model.movies.Movie
import com.example.moviesapp.databinding.PopularMoviesLayoutBinding

class MoviesAdapter(
    private var movieList: List<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding =
            PopularMoviesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movieList = movieList[position]
        holder.bind(movieList,onItemClick)
    }

    fun setList(newMovieList: List<Movie>) {
        movieList = newMovieList
        notifyDataSetChanged()
    }

    class MoviesViewHolder(
        private val binding: PopularMoviesLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, onItemClick: (Movie) -> Unit) {
            binding.tvMovieTitle.text = movie.title
            Glide.with(binding.root).load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .error(R.drawable.ic_error)
                .into(binding.ivMoviePoster)

            binding.ivMoviePoster.setOnClickListener {
                onItemClick(movie)
            }
        }
    }
}