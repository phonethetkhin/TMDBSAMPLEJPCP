package com.ptk.tmdb_sample_jpcp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.tmdb_sample_jpcp.model.RemoteResource
import com.ptk.tmdb_sample_jpcp.model.dto.MovieModel
import com.ptk.tmdb_sample_jpcp.repository.HomeRepository
import com.ptk.tmdb_sample_jpcp.ui.ui_states.HomeUIStates
import com.ptk.tmdb_sample_jpcp.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val application: Application

) : ViewModel() {
    private val _uiStates = MutableStateFlow(HomeUIStates())
    val uiStates = _uiStates.asStateFlow()


    fun getPopularMovies(

    ) {
        repository.getPopularMovies().onEach { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading ->
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }

                is RemoteResource.Success -> {
                    Log.e("success", "success")

                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            recommendList = remoteResource.data.results
                        )
                    }
                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            errorMessage = "Something Went Wrong !!!"
                        )
                    }
                    application.showToast(remoteResource.errorMessage.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getNowPlayingMovies() {
        repository.getNowPlayingMovies().onEach { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading ->
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }

                is RemoteResource.Success -> {
                    Log.e("success", "success")

                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            nowPlayingList = remoteResource.data.results
                        )
                    }
                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            errorMessage = "Something Went Wrong !!!"
                        )
                    }
                    application.showToast(remoteResource.errorMessage.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUpcomingList(

    ) {
        repository.getUpcomingMovies().onEach { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading ->
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }

                is RemoteResource.Success -> {
                    Log.e("success", "success")

                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            upcomingList = remoteResource.data.results
                        )
                    }
                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            errorMessage = "Something Went Wrong !!!"
                        )
                    }
                    application.showToast(remoteResource.errorMessage.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun toggleFav(movieId: Int) {
        val movieModel = _uiStates.value.recommendList.find { it.id == movieId }
        val movieModel2 = _uiStates.value.nowPlayingList.find { it.id == movieId }
        val movieModel3 = _uiStates.value.upcomingList.find { it.id == movieId }

        _uiStates.update {
            it.copy(recommendList = _uiStates.value.recommendList.mapIndexed { index, details ->
                if (_uiStates.value.recommendList.indexOf(movieModel) == index) details.copy(
                    isFav = !details.isFav
                )
                else details
            } as ArrayList<MovieModel>)
        }

        _uiStates.update {
            it.copy(nowPlayingList = _uiStates.value.nowPlayingList.mapIndexed { index, details ->
                if (_uiStates.value.nowPlayingList.indexOf(movieModel2) == index) details.copy(
                    isFav = !details.isFav
                )
                else details
            } as ArrayList<MovieModel>)
        }

        _uiStates.update {
            it.copy(upcomingList = _uiStates.value.upcomingList.mapIndexed { index, details ->
                if (_uiStates.value.upcomingList.indexOf(movieModel3) == index) details.copy(
                    isFav = !details.isFav
                )
                else details
            } as ArrayList<MovieModel>)
        }

    }

}
