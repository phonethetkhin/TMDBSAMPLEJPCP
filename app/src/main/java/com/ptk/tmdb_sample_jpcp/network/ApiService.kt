package com.ptk.tmdb_sample_jpcp.network

import com.ptk.tmdb_sample_jpcp.model.dto.MovieResponseModel


public interface ApiService {

    suspend fun getPopularMovies(): MovieResponseModel
    suspend fun getNowPlayingMovies(): MovieResponseModel
    suspend fun getUpcomingMovies(): MovieResponseModel


}