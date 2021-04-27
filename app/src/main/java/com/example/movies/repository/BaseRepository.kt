package com.example.wetherforecast.repository

import com.example.wetherforecast.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T>{
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            }catch (throwable:Throwable){
                when(throwable){
                    is HttpException -> {
                        Resource.failure(false,throwable.response()?.errorBody(),throwable.code())
                    }
                   else ->{
                        Resource.failure(true,null,null)
                    }
                }

            }
        }

    }
}
