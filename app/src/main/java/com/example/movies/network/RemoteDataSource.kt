package com.example.wetherforecast.network

import com.example.movies.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSource {
    companion object {
        const val baseUrl = "https://api.themoviedb.org/3/"
        const val appKey = "04e304b1afca5efa05c408bffc5be3a4"
    }

    fun <Api> buildApi(
        api: Class<Api>
    ):Api {
        val logging = HttpLoggingInterceptor()
        logging.apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()

            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())//Moshi
            .build()
            .create(api)
    }
}