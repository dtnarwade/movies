package com.example.movies.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movies.databinding.MoviesAdapterLayoutBinding
import com.example.movies.model.Movies
import com.example.movies.ui.adapters.MoviesAdapter.MovieViewHolder
import androidx.paging.PagedListAdapter

class MoviesAdapter : PagedListAdapter<Movies, MovieViewHolder>(diffCallback) {
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

    fun setBaseUrl(baseUrl: String) {
        imageBaseUrl = baseUrl
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding =
            MoviesAdapterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, imageBaseUrl) }
    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Movies>() {
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean =
                oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean =
                oldItem == newItem
        }
    }
}