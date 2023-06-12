package com.example.moviesapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.data.model.database.MovieLocal
import com.example.moviesapp.data.model.movies.MovieDto
import com.example.moviesapp.databinding.PopularMoviesLayoutBinding

class MoviesAdapter(
    private var movieDtoList: List<MovieDto>,
    private val onItemClick: (MovieDto) -> Unit,
    private val onLastItemReached: () -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding =
            PopularMoviesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieDtoList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movieList = movieDtoList[position]
        holder.bind(movieList,onItemClick)
        if (position == itemCount - 1) {
            onLastItemReached.invoke()
        }
    }

    fun setList(newMovieListDto: List<MovieDto>) {
        movieDtoList = newMovieListDto
        notifyDataSetChanged()
    }

    fun setDatabaseMovieList(databaseMovieLocals: List<MovieLocal>) {
        movieDtoList = databaseMovieLocals.map { movieLocal ->
            MovieDto(
                id = movieLocal.id,
                title = movieLocal.title,
                overview = movieLocal.overview,
                posterPath = movieLocal.posterPath,
                releaseDate = movieLocal.releaseDate,
                originalLanguage = movieLocal.originalLanguage
            )
        }
        notifyDataSetChanged()
    }

    class MoviesViewHolder(
        private val binding: PopularMoviesLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieDto: MovieDto, onItemClick: (MovieDto) -> Unit) {
            binding.tvMovieTitle.text = movieDto.title
            if (movieDto.posterPath != null) {
                Glide.with(binding.root).load("https://image.tmdb.org/t/p/w500${movieDto.posterPath}")
                    .error(R.drawable.ic_error)
                    .into(binding.ivMoviePoster)
            }


            binding.ivMoviePoster.setOnClickListener {
                onItemClick(movieDto)
            }
        }
    }
}