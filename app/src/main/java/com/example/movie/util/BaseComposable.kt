package com.example.movie.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun <State,Event , reified Vm: BaseViewModel<Event,State>> BaseComposable(
    withDefaultLoading:Boolean = true,
    loadingView : @Composable ()-> Unit = {
        CircularProgressIndicator(
        )
    },
    contentView:@Composable (state:State , viewmodel:Vm,isLoading: Boolean)-> Unit
){
    val viewModel = koinViewModel<Vm>()
    val rootState by viewModel.state.collectAsState()
    Box (
        modifier = Modifier.fillMaxSize(
        ),
    ){
        contentView(rootState.data,viewModel,rootState.isLoading)
        if(rootState.isLoading && withDefaultLoading)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)).clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {

                        },

                    )
                , // dim background (optional)
                contentAlignment = Alignment.Center
            ) {
                loadingView()
            }
    }
}