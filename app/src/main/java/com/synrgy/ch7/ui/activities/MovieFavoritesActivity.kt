package com.synrgy.ch7.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.ch7.R
import com.synrgy.ch7.data.local.model.Movie
import com.synrgy.ch7.databinding.ActivityMovieFavoritesBinding
import com.synrgy.ch7.ui.adapter.FavoriteMoviesAdapter

class MovieFavoritesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteMoviesAdapter
    private lateinit var binding: ActivityMovieFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerViewFavorites)

        // Load favorite movies from SharedPreferences
        val sharedPreferences = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val favoriteMovies = sharedPreferences.getStringSet("favorite_movies", setOf()) ?: setOf()

        val movieList = favoriteMovies.mapNotNull { entry ->
            val movieDetails = entry.split("|")

            if (movieDetails.size >= 7) { // Ensure enough details are present
                Movie(
                    id = movieDetails[0].toLongOrNull() ?: 0L,
                    title = movieDetails[1],
                    posterPath = movieDetails[2],
                    backdropPath = movieDetails[3], // Ensure this is included
                    rating = movieDetails[4].toFloatOrNull() ?: 0f,
                    releaseDate = movieDetails[5],
                    overview = movieDetails[6]
                )
            } else {
                null
            }
        }


        adapter = FavoriteMoviesAdapter(movieList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

}
