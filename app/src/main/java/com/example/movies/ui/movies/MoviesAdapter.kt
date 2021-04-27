package com.example.movies.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movies.databinding.MoviesAdapterLayoutBinding
import com.example.movies.model.Movies
import com.example.movies.ui.movies.MoviesAdapter.MovieViewHolder

class MoviesAdapter(var movies: List<Movies>) :
    RecyclerView.Adapter<MovieViewHolder>() {
    var imageBaseUrl: String = ""

    class MovieViewHolder(private val itemBinding: MoviesAdapterLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: Movies, baseUrl: String) {

            Glide.with(itemBinding.imageMoviePoster.context)
                .load(baseUrl + movie.poster_path)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemBinding.imageMoviePoster)

        }
    }

    fun setBaseUrl(baseUrl: String): Unit {
        imageBaseUrl = baseUrl
        notifyDataSetChanged()
    }
    fun setPopularMovies(popularMovies:List<Movies>): Unit {
        movies = popularMovies
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding =
            MoviesAdapterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], imageBaseUrl)
    }


}