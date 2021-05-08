package com.example.movies.ui.movies

import androidx.paging.PageKeyedDataSource
import com.example.movies.model.Movies
import com.example.movies.util.Util
import com.example.wetherforecast.network.Resource
import com.example.wetherforecast.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MoviesDataSource(var moviesRepo: MoviesRepository,private val scope: CoroutineScope) : PageKeyedDataSource<Int, Movies>() {

    override fun loadInitial(params: LoadInitialParams<Int>,callback: LoadInitialCallback<Int, Movies>) {
        scope.launch {
            val response = moviesRepo.getPopularMovies(1, Util.API_KEY)
            when (response) {
                is Resource.Success -> {
                    callback.onResult(response.rspononse.results,null,2)
                }
                is Resource.failure -> {

                }
            }

        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        scope.launch {
            val response = moviesRepo.getPopularMovies(params.key, Util.API_KEY)
            when (response) {
                is Resource.Success -> {
                     val adjacentKey = if (params.key > 1) {params.key + 1} else null
                    callback.onResult(response.rspononse.results,adjacentKey)
                }
                is Resource.failure -> {

                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        scope.launch {

            val response = moviesRepo.getPopularMovies(params.key, Util.API_KEY)
            when (response) {
                is Resource.Success -> {
                    val adjacentKey = if (params.key < response.rspononse.total_pages) {params.key - 1} else null
                    callback.onResult(response.rspononse.results,adjacentKey)
                }
                is Resource.failure -> {

                }
            }
        }
    }

}