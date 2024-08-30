package com.synrgy.ch7.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.synrgy.ch7.R
import com.synrgy.ch7.databinding.ActivityMovieDetailsBinding

const val MOVIE_ID = "extra_movie_id"
const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    private lateinit var fabFavorite: FloatingActionButton
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)
        fabFavorite = findViewById(R.id.fabfavmovie)

        setupClickListeners()

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

        fabFavorite.setOnClickListener {
            addToFavorites(extras)
        }
    }

    private fun setupClickListeners() = with(binding) {
        topAppBar.setOnClickListener {
            startActivity(Intent(this@MovieDetailsActivity, MainActivity::class.java))
        }
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
    }

    private fun addToFavorites(extras: Bundle?) {
        if (extras == null) return

        val sharedPreferences = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Ambil data film dari Bundle
        val movieId = extras.getLong(MOVIE_ID, 0L)
        val movieTitle = extras.getString(MOVIE_TITLE, "")
        val moviePoster = extras.getString(MOVIE_POSTER, "")
        val movieBackdrop = extras.getString(MOVIE_BACKDROP, "") // Ensure this is obtained
        val movieRating = extras.getFloat(MOVIE_RATING, 0f)
        val movieReleaseDate = extras.getString(MOVIE_RELEASE_DATE, "")
        val movieOverview = extras.getString(MOVIE_OVERVIEW, "")

        // Format data yang akan disimpan
        val movieEntry = "$movieId|$movieTitle|$moviePoster|$movieBackdrop|$movieRating|$movieReleaseDate|$movieOverview"

        // Ambil set film favorit yang ada dan tambahkan film baru
        val favoriteMovies = sharedPreferences.getStringSet("favorite_movies", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        if (favoriteMovies.add(movieEntry)) {
            editor.putStringSet("favorite_movies", favoriteMovies)
            editor.apply()
            Toast.makeText(this, "$movieTitle added to favorites", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "$movieTitle is already in favorites", Toast.LENGTH_SHORT).show()
        }
    }

}
