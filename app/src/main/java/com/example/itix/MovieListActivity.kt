package com.example.itix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.itix.databinding.ActivityMovieListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class   MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private lateinit var db: AppDatabase
    private lateinit var nowPlayingAdapter: MovieAdapter
    private lateinit var topMovieAdapter: MovieAdapter
    private lateinit var comingSoonAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        nowPlayingAdapter = MovieAdapter(emptyList()) { toggleFavorite(it) }
        topMovieAdapter = MovieAdapter(emptyList()) { toggleFavorite(it) }
        comingSoonAdapter = MovieAdapter(emptyList()) { toggleFavorite(it) }

        binding.rvNowPlaying.layoutManager = GridLayoutManager(this, 3)
        binding.rvNowPlaying.adapter = nowPlayingAdapter

        binding.rvTopMovie.layoutManager = GridLayoutManager(this, 3)
        binding.rvTopMovie.adapter = topMovieAdapter

        binding.rvComingSoon.layoutManager = GridLayoutManager(this, 3)
        binding.rvComingSoon.adapter = comingSoonAdapter

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val dao = db.movieDao()
                if (dao.getMoviesByCategory("Now Playing").isEmpty()) {
                    dao.insertAll(dummyMovies())
                }
            }
            loadMovies()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private suspend fun loadMovies() {
        val dao = db.movieDao()
        val nowPlaying = dao.getMoviesByCategory("Now Playing")
        val topMovie = dao.getMoviesByCategory("Top Movie")
        val comingSoon = dao.getMoviesByCategory("Coming Soon")

        nowPlayingAdapter.updateList(nowPlaying)
        topMovieAdapter.updateList(topMovie)
        comingSoonAdapter.updateList(comingSoon)
    }

    private fun toggleFavorite(movie: Movie) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.movieDao().updateMovie(movie)
            }
            loadMovies()
        }
    }


    private fun dummyMovies(): List<Movie> = listOf(
        Movie(title = "Lilo & Stitch", imageRes = R.drawable.lilostitch, category = "Now Playing", genre = "Action / Comedy", duration = "120 min"),
        Movie(title = "How to Train Your Dragon", imageRes = R.drawable.movie_httyd, category = "Now Playing", genre = "Action / Comedy", duration = "120 min"),
        Movie(title = "F1: The Movie", imageRes = R.drawable.movie_f1, category = "Now Playing", genre = "Action / Comedy", duration = "120 min"),
        Movie(title = "Jurassic World: Rebirth", imageRes = R.drawable.movie_jw, category = "Top Movie", genre = "Action / Comedy", duration = "120 min"),
        Movie(title = "Superman", imageRes = R.drawable.movie_supr, category = "Top Movie", genre = "Action / Comedy", duration = "120 min"),
        Movie(title = "Karate Kid Legends", imageRes = R.drawable.movie_kk, category = "Top Movie", genre = "Action / Comedy", duration = "120 min"),
        Movie(title = "WARFARE", imageRes = R.drawable.movie_war, category = "Coming Soon", genre = "Action / Comedy", duration = "120 min"),
        Movie(title = "Blood Brothers", imageRes = R.drawable.movie_brother, category = "Coming Soon", genre = "Action / Comedy", duration = "120 min"),
        Movie(title = "Fear Below", imageRes = R.drawable.movie_fear, category = "Coming Soon", genre = "Action / Comedy", duration = "120 min")
    )
}
