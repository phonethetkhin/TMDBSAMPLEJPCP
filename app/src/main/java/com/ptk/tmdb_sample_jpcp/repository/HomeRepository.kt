package com.ptk.tmdb_sample_jpcp.repository

import android.app.Application
import com.ptk.tmdb_sample_jpcp.R
import com.ptk.tmdb_sample_jpcp.model.RemoteResource
import com.ptk.tmdb_sample_jpcp.network.ApiService
import io.ktor.network.sockets.ConnectTimeoutException
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val application: Application,
) {
    fun getPopularMovies(
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getPopularMovies()
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))
                }
            }
        }
    }

    fun getNowPlayingMovies(
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getNowPlayingMovies()
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))
                }
            }
        }
    }

    fun getUpcomingMovies(
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getUpcomingMovies()
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }

                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))
                }
            }
        }
    }
}
