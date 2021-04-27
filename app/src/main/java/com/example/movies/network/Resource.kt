package com.example.wetherforecast.network

import okhttp3.ResponseBody


sealed class Resource<out T>{
    data class Success<out T>(val rspononse:T) : Resource<T>()
    data class failure(val isNetworkError:Boolean,var rspononsebody:ResponseBody?,var statusCode:Int?) : Resource<Nothing>()
}