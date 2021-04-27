package com.example.wetherforecast.network

import com.example.movies.model.responses.ConfigurationResponse
import com.example.movies.model.responses.MoviesResponse
import retrofit2.http.*

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") appid: String
    ): MoviesResponse

    @GET("configuration")
    suspend fun getConfigurations(
        @Query("api_key") appid: String
    ): ConfigurationResponse
}