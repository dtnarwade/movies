package com.example.movies.model.responses
import com.example.movies.model.Movies
data class MoviesResponse(var page: Int,var results:List<Movies>,var total_pages: Int,var total_results:Int)