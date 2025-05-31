package com.example.itix

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itix.databinding.ItemMovieBinding

class MovieAdapter(
    private var movies: List<Movie>,
    private val onFavoriteClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        with(holder.binding) {
            imageMovie.setImageResource(movie.imageRes)
            textTitle.text = movie.title

            ivFavorite.setImageResource(
                if (movie.isFavorite) R.drawable.ic_star else R.drawable.ic_star_border
            )

            ivFavorite.setOnClickListener {
                val updatedMovie = movie.copy(isFavorite = !movie.isFavorite)
                onFavoriteClick(updatedMovie)
            }
        }
    }

    fun updateList(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}
