package com.ptk.tmdb_sample_jpcp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ptk.tmdb_sample_jpcp.R
import com.ptk.tmdb_sample_jpcp.model.dto.CastModel
import com.ptk.tmdb_sample_jpcp.model.dto.MovieDetailResponseModel
import com.ptk.tmdb_sample_jpcp.ui.ui_resource.theme.LightBlue
import com.ptk.tmdb_sample_jpcp.ui.ui_resource.theme.Pink
import com.ptk.tmdb_sample_jpcp.ui.ui_states.DetailUIStates
import com.ptk.tmdb_sample_jpcp.util.Constants
import com.ptk.tmdb_sample_jpcp.util.convertMinutesToHoursAndMinutes
import com.ptk.tmdb_sample_jpcp.util.getConvertDate
import com.ptk.tmdb_sample_jpcp.viewmodel.DetailViewModel
import com.ptk.tmdb_sample_jpcp.viewmodel.HomeViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


//UIs
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    movieId: Int,
    homeViewModel: HomeViewModel,
    isFav: Boolean,
    status: Int,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val uiStates by detailViewModel.uiStates.collectAsState()

    LaunchedEffect(key1 = "") {
        detailViewModel.getMovieDetail(movieId, isFav)
        detailViewModel.getCasts(movieId)
    }

    uiStates.detailResponseModel?.let {

        DetailScreenContent(
            navController = navController,
            uiStates = uiStates,
            movie = it,
            homeViewModel,
            detailViewModel,
            status
        )
    }

}

@Composable
fun DetailScreenContent(
    navController: NavController,
    uiStates: DetailUIStates,
    movie: MovieDetailResponseModel,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel,
    status: Int,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Movie Cover Photo
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.IMAGE_PATH_HELPER + movie.backdropPath)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)

        )

        Spacer(modifier = Modifier.height(16.sdp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${movie.title}",
                style = TextStyle(fontSize = 20.ssp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.sdp, end = 8.sdp)
            )
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = if (movie.isFav) Color.Red else Color.Gray,
                modifier = Modifier
                    .padding(end = 8.sdp)
                    .clickable {
                        detailViewModel.toggleFav()
                        homeViewModel.toggleFav(movie.id!!, status)
                    },
            )
            Text(
                text = "7.7%",
                style = TextStyle(fontSize = 14.ssp),
                modifier = Modifier
                    .padding(end = 16.sdp)
            )

        }

        Spacer(modifier = Modifier.height(8.sdp))

        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "${movie.productionCountries?.get(0)?.iso31661 ?: "-"} | ${movie.releaseDate?.getConvertDate() ?: "-"}",
                style = TextStyle(fontSize = 14.ssp),
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.sdp, end = 8.sdp)
            )

            Text(
                text = "${movie.voteCount} votes",
                style = TextStyle(fontSize = 14.ssp),
                modifier = Modifier
                    .padding(end = 16.sdp)
            )

        }

        Spacer(modifier = Modifier.height(8.sdp))

        val (hours, minutes) = movie.runtime!!.convertMinutesToHoursAndMinutes()
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "${hours}hr ${minutes}min | ${
                    movie.genres?.map { movie.title }?.joinToString(separator = ",")
                }",
                style = TextStyle(fontSize = 14.ssp),
                color = LightBlue,
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.sdp, end = 8.sdp)
            )
            Text(
                text = "${movie.spokenLanguages?.get(0)?.name}",
                style = TextStyle(fontSize = 14.ssp),
                modifier = Modifier
                    .padding(end = 16.sdp)
            )

        }
        Spacer(modifier = Modifier.height(32.sdp))
        Text(
            text = "Movie Description",
            style = TextStyle(fontSize = 16.ssp),
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.sdp, end = 8.sdp)
        )
        Spacer(modifier = Modifier.height(8.sdp))
        Text(
            text = "${movie.overview}",
            style = TextStyle(fontSize = 14.ssp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.sdp, end = 8.sdp)
        )
        Spacer(modifier = Modifier.height(32.sdp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Cast",
                style = TextStyle(fontSize = 16.ssp),
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 8.sdp, end = 8.sdp)
                    .weight(1F)
            )

            TextButton(onClick = {}) {
                Text(
                    text = "View All",
                    style = TextStyle(fontSize = 14.ssp),
                    color = Pink,
                )
            }
        }
        Spacer(modifier = Modifier.height(8.sdp))
        uiStates.castModel?.let { castModel ->
            LazyRow(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(castModel.cast) { cModel ->
                    CastListItem(castModel = cModel)
                }
            }
        }
        Button(
            onClick = {},
            modifier = Modifier
                .padding(16.dp)
                .background(LightBlue, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp)) // Set the corner radius here
        ) {
            Text(
                text = "Book Tickets",
                style = TextStyle(fontSize = 14.ssp),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun CastListItem(castModel: CastModel) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = LightBlue,
        ),
    ) {
        Column(
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.IMAGE_PATH_HELPER + castModel.profilePath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "Cast Photo",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = castModel.originalName ?: "-",
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

        }
    }
}



