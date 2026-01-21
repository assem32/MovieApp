package com.example.movie

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movie.data.remote.ApiService
import com.example.movie.presentation.home.HomeView
import com.example.movie.presentation.home.HomeViewModel
import com.example.movie.ui.theme.MovieTheme
import com.example.movie.util.navigation.NavGraph
import com.example.movie.util.navigation.Routes
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val movieViewModel: HomeViewModel by viewModel()
    private val apiService: ApiService by inject ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gradientBrush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF1A1A1A), // Dark Gray/Black
                Color(0xFF4A148C)  // Deep Purple
            )
        )
        enableEdgeToEdge()
        setContent {
            LaunchedEffect (Unit) {
                try {
                    val response = apiService.getMovies()
                    Log.d("dfff",""+response)
                } catch (e: Exception) {
                    println("TEST_FAILURE: ${e.message}")
                }
            }
            MovieTheme {
                Scaffold(modifier = Modifier.fillMaxSize().background(gradientBrush),
                    containerColor = Color.Transparent,
                ) { innerPadding ->
                    NavGraph(startRoute1 = Routes.homeRoute, innerPadding)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieTheme {
        Greeting("Android")
    }
}