package com.example.wetherforecast.repository

import com.example.wetherforecast.network.MoviesApi

class MoviesRepository(private val api:MoviesApi) : BaseRepository() {

    suspend fun getPopularMovies(page:Int, APIKay:String) = safeApiCall {
        api.getPopularMovies(page,APIKay)
    }

    suspend fun getImageConfiguration(APIKay:String) = safeApiCall {
        api.getConfigurations(APIKay)
    }
}