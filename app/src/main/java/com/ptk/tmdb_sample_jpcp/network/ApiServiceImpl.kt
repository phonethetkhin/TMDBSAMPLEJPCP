package com.ptk.tmdb_sample_jpcp.network


import com.ptk.tmdb_sample_jpcp.model.dto.CastResponseModel
import com.ptk.tmdb_sample_jpcp.model.dto.MovieDetailResponseModel
import com.ptk.tmdb_sample_jpcp.model.dto.MovieResponseModel
import com.ptk.tmdb_sample_jpcp.util.Constants.BASE_URL
import com.ptk.tmdb_sample_jpcp.util.Constants.TOKEN
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
public class ApiServiceImpl @Inject constructor(
    private val client: HttpClient,
) : ApiService {

    override suspend fun getPopularMovies(): MovieResponseModel = client.get {
        url(BASE_URL + APIRoutes.getPopularMovies)
        header("Authorization", "Bearer $TOKEN")
        contentType(ContentType.Application.Json)
        parameter("page", 1)
    }

    override suspend fun getNowPlayingMovies(): MovieResponseModel = client.get {
        url(BASE_URL + APIRoutes.getNowPlayingMovies)
        header("Authorization", "Bearer $TOKEN")
        contentType(ContentType.Application.Json)
        parameter("page", 1)
    }

    override suspend fun getUpcomingMovies(): MovieResponseModel = client.get {
        url(BASE_URL + APIRoutes.getUpcomingMovies)
        header("Authorization", "Bearer $TOKEN")
        contentType(ContentType.Application.Json)
        parameter("page", 1)
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailResponseModel = client.get {
        url(BASE_URL + APIRoutes.getMovieDetail + movieId)
        header("Authorization", "Bearer $TOKEN")
        contentType(ContentType.Application.Json)
        parameter("page", 1)
    }

    override suspend fun getCasts(movieId: Int): CastResponseModel = client.get {
        url(BASE_URL + APIRoutes.getMovieDetail + movieId + "/credits")
        header("Authorization", "Bearer $TOKEN")
        contentType(ContentType.Application.Json)
        parameter("page", 1)
    }

}