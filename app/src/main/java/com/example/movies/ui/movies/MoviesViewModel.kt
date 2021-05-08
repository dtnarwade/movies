package com.example.movies.ui.movies

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movies.model.Images
import com.example.movies.model.Movies
import com.example.movies.model.responses.ConfigurationResponse
import com.example.movies.model.responses.MoviesResponse
import com.example.movies.util.Util
import com.example.wetherforecast.network.MoviesApi
import com.example.wetherforecast.network.RemoteDataSource
import com.example.wetherforecast.network.Resource
import com.example.wetherforecast.repository.MoviesRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    var imageConfig: MutableLiveData<Images> = MutableLiveData()
    var remoteDataSource = RemoteDataSource()
    private var moviesRepo: MoviesRepository
    private val _configResponse: MutableLiveData<Resource<ConfigurationResponse>> = MutableLiveData()
    val configResponse: LiveData<Resource<ConfigurationResponse>>
        get() = _configResponse


    init {
        moviesRepo = MoviesRepository(remoteDataSource.buildApi(MoviesApi::class.java))
    }

    fun setImageConfig(config: String) {
        val image: Images?
        try {
            image = Gson().fromJson(config, Images::class.java)
            imageConfig.value = image
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveImageConfig(image: Images) {
        imageConfig.value = image
    }

    fun getImageConfig() {
        viewModelScope.launch {
            _configResponse.value = moviesRepo.getImageConfiguration(Util.API_KEY)
        }
    }

    var postsLiveData  :LiveData<PagedList<Movies>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData  = initializedPagedListBuilder(config).build()
    }

    fun getPosts():LiveData<PagedList<Movies>> = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Movies> {

        val dataSourceFactory = object : DataSource.Factory<Int, Movies>() {
            override fun create(): DataSource<Int, Movies> {
                return MoviesDataSource(moviesRepo,viewModelScope)
            }
        }
        return LivePagedListBuilder<Int, Movies>(dataSourceFactory, config)
    }
}
