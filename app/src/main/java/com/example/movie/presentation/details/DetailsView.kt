package com.example.movie.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.movie.R
import com.example.movie.util.UiSideEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: DetailsState.State = DetailsState.State(),
    onEvent: (DetailsState.DetailsEvent) -> Unit = {},
    effect: Flow<UiSideEffect> = emptyFlow()
) {
    LaunchedEffect(Unit) {
        onEvent(DetailsState.DetailsEvent.OnInitScreen(movieId = movieId))
    }

    var viewSheet by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(effect) {
        effect.collect { sideEffect ->
            when (sideEffect) {
                is DetailsState.DetailsSideEffect.GetDetailError -> {
                    viewSheet = true
                }
            }
        }
    }

    if (viewSheet) {
        ModalBottomSheet(
            containerColor =  MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            sheetState = sheetState,
            onDismissRequest = { viewSheet = false }
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
            ){
                Image(
                    painter = painterResource(R.drawable.ic_error),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                )
                Text("Error Happened")
            }
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original${state.detailsModel?.poster_path}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().clip(
                    shape = RoundedCornerShape(
                        16.dp
                    )
                )
            )

            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(top = 48.dp, start = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .offset(y = (-20).dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.detailsModel?.title ?: "",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = " ${
                        String.format(
                            "%.1f",
                            state.detailsModel?.vote_average 
                        )
                    }  |  ${state.detailsModel?.release_date?:""}",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Overview",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.detailsModel?.overview ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.LightGray,
                lineHeight = 24.sp
            )
        }
    }
}

@Preview
@Composable
fun MovieDetailsScreenPreview(){
    MovieDetailsScreen("12",{})
}