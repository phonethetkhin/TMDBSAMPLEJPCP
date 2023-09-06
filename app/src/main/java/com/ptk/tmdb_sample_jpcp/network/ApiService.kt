package com.ptk.tmdb_sample_jpcp.network

import com.ptk.tmdb_sample_jpcp.model.dto.CastResponseModel
import com.ptk.tmdb_sample_jpcp.model.dto.MovieDetailResponseModel
import com.ptk.tmdb_sample_jpcp.model.dto.MovieResponseModel


public interface ApiService {

    suspend fun getPopularMovies(): MovieResponseModel
    suspend fun getNowPlayingMovies(): MovieResponseModel
    suspend fun getUpcomingMovies(): MovieResponseModel

    suspend fun getMovieDetail(movieId: Int): MovieDetailResponseModel
    suspend fun getCasts(movieId: Int): CastResponseModel


}