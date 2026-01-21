package com.example.movie.util.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movie.presentation.details.DetailsState
import com.example.movie.presentation.details.DetailsViewModel
import com.example.movie.presentation.details.MovieDetailsScreen
import com.example.movie.presentation.home.HomeContract
import com.example.movie.presentation.home.HomeView
import com.example.movie.presentation.home.HomeViewModel
import com.example.movie.util.BaseComposable

@Composable
fun NavGraph(startRoute1: String, padding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startRoute1) {

        composable(Routes.homeRoute) {
            BaseComposable<HomeContract.HomeSata, HomeContract.HomeEvent, HomeViewModel> { state, viewmodel, isLoading ->
                HomeView(
                    modifier = Modifier.padding(padding),
                    state = state,
                    effect = viewmodel.effect,
                    onEvent = viewmodel::setEvent,
                    navigateAction = object : HomeContract.NavigationAction {
                        override fun navigateToDetails(id: String) {
                            navController.navigate("${Routes.detailsRoute}/$id")
                        }

                    }
                )
            }
        }

        composable(
            "${Routes.detailsRoute}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("movieId") ?: ""
            BaseComposable<DetailsState.State, DetailsState.DetailsEvent, DetailsViewModel> { state, viewmodel, isLoading ->
                MovieDetailsScreen(
                    modifier = Modifier.padding(padding),
                    movieId = id,
                    state = state,
                    effect = viewmodel.effect,
                    onEvent = viewmodel::setEvent,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}