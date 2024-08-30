package com.synrgy.ch7.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synrgy.ch7.data.local.model.Movie
import com.synrgy.ch7.databinding.ItemFavoriteMovieBinding

class FavoriteMoviesAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<FavoriteMoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(private val binding: ItemFavoriteMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            Glide.with(binding.moviePoster.context)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .into(binding.moviePoster)

            binding.movieTitle.text = movie.title
            binding.movieReleaseDate.text = movie.releaseDate
            binding.movieRating.rating = movie.rating / 2 // Assuming rating is out of 10
            binding.movieOverview.text = movie.overview
        }
    }
}
