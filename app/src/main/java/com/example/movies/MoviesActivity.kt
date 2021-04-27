package com.example.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movies.databinding.MoviesActivityBinding
import com.example.movies.ui.movies.MoviesFragment

class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: MoviesActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MoviesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
