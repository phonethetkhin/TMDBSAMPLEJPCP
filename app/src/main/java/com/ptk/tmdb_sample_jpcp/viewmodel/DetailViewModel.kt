package com.ptk.tmdb_sample_jpcp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.tmdb_sample_jpcp.model.RemoteResource
import com.ptk.tmdb_sample_jpcp.repository.DetailRepository
import com.ptk.tmdb_sample_jpcp.ui.ui_states.DetailUIStates
import com.ptk.tmdb_sample_jpcp.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository,
    private val application: Application

) : ViewModel() {
    private val _uiStates = MutableStateFlow(DetailUIStates())
    val uiStates = _uiStates.asStateFlow()

    fun toggleFav() {
        _uiStates.update { currentState ->
            currentState.copy(
                detailResponseModel = currentState.detailResponseModel!!.copy(isFav = !currentState.detailResponseModel.isFav)
            )
        }
    }

    fun getMovieDetail(
        movieId: Int,
        isFav: Boolean,
    ) {
        repository.getMovieDetail(movieId).onEach { remoteResource ->
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
                            detailResponseModel = remoteResource.data.copy(isFav = isFav)
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

    fun getCasts(
        movieId: Int
    ) {
        repository.getCasts(movieId).onEach { remoteResource ->
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
                            castModel = remoteResource.data
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


}
