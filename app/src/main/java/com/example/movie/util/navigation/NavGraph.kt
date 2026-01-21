package com.example.movie.util.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movie.presentation.home.HomeContract
import com.example.movie.presentation.home.HomeView
import com.example.movie.presentation.home.HomeViewModel
import com.example.movie.util.BaseComposable

@Composable
fun NavGraph(startRoute1: String, padding: PaddingValues){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startRoute1) {

        composable(Routes.homeRoute) {
            BaseComposable<HomeContract.HomeSata, HomeContract.HomeEvent, HomeViewModel> { state, viewmodel, isLoading ->
                HomeView(
                    modifier = Modifier.padding(padding),
                    state = state,
                    effect = viewmodel.effect,
                    onEvent = viewmodel::setEvent,

                )
            }
        }
    }
}