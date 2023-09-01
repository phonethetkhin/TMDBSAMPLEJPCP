package com.ptk.tmdb_sample_jpcp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ptk.tmdb_sample_jpcp.R
import com.ptk.tmdb_sample_jpcp.model.dto.MovieModel
import com.ptk.tmdb_sample_jpcp.ui.ui_resource.theme.Green
import com.ptk.tmdb_sample_jpcp.ui.ui_states.HomeUIStates
import com.ptk.tmdb_sample_jpcp.util.Constants
import com.ptk.tmdb_sample_jpcp.viewmodel.HomeViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


//UIs
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiStates by homeViewModel.uiStates.collectAsState()

    LaunchedEffect(key1 = "") {
        withContext(Dispatchers.IO) { homeViewModel.getPopularMovies() }
        withContext(Dispatchers.IO) { homeViewModel.getNowPlayingMovies() }
        withContext(Dispatchers.IO) { homeViewModel.getUpcomingList() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Green),
                title = {
                    Text(
                        "Home",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.ssp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.sdp),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {

                }
            )
        }
    ) {
        HomeScreenContent(navController, it.calculateTopPadding().value, uiStates)

    }

}

@Composable
fun HomeScreenContent(navController: NavController, topMargin: Float, uiStates: HomeUIStates) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = topMargin.dp)
    ) {
        item { RecommendedMoviesUI("Recommended Movies", uiStates.recommendList) }
        item { RecommendedMoviesUI("Now Playing Movies", uiStates.nowPlayingList) }
        item { RecommendedMoviesUI("Upcoming Movies", uiStates.upcomingList) }

    }

}

@Composable
fun RecommendedMoviesUI(title: String, list: ArrayList<MovieModel>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.sdp)
    ) {
        Text(
            text = title,
            fontSize = 16.ssp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.sdp)
        )

        MovieList(movies = list)
    }
}

@Composable
fun MovieList(movies: ArrayList<MovieModel>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(movies) { movie ->
            MovieListItem(movie = movie)
        }
    }
}

@Composable
fun MovieListItem(movie: MovieModel) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Green,
        ),
    ) {
        Column(
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.IMAGE_PATH_HELPER + movie.posterPath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "Movie Poster",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.title ?: "-",
                fontWeight = FontWeight.Bold,
                fontSize = 12.ssp,
                color = Color.White,
                textAlign = TextAlign.Center,
                minLines = 2,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
//                    tint = if (movie.isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "${movie.voteAverage}%",
                    fontSize = 12.ssp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}


