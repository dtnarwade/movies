package com.example.movies.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.data.ConfigurationPreferences
import com.example.movies.databinding.MoviesFragmentBinding
import com.example.movies.ui.adapters.MoviesAdapter
import com.example.wetherforecast.network.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesFragment : Fragment() {
    var binding: MoviesFragmentBinding? = null

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private lateinit var viewModel: MoviesViewModel
    private lateinit var configPref: ConfigurationPreferences
    private lateinit var moviesAdapter: MoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = MoviesFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        configPref = ConfigurationPreferences(requireContext())

        configPref.imageConfig.asLiveData().observe(viewLifecycleOwner, Observer {
            if (it == null) {
                viewModel.getImageConfig()
            } else {
                viewModel.setImageConfig(it)
            }
        })



        viewModel.imageConfig.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val url = it.secure_base_url + it.poster_sizes?.size?.minus(6)?.let { it1 ->
                    it.poster_sizes?.get(
                       it1
                    )
                }
                moviesAdapter.setBaseUrl(url)
            }
        })

        viewModel.getPosts().observe(viewLifecycleOwner, Observer {
            moviesAdapter.submitList(it)
        })
        viewModel.configResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.rspononse.images?.let { it1 ->
                        viewModel.saveImageConfig(it1)
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                configPref.saveImageConfiguration(Gson().toJson(it1).toString())
                            }
                        }
                    }
                }
                is Resource.failure -> {
                    Toast.makeText(
                        requireContext(),
                        "failure in Image configurations",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })


        showMovies()

        return binding?.root
    }

    private fun showMovies() {
        binding?.RvMovies?.apply {
            val lm = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            moviesAdapter = MoviesAdapter()
            layoutManager = lm
            itemAnimator = DefaultItemAnimator()
            adapter = moviesAdapter

        }
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        binding = null
        super.onDestroyView()
    }

}
