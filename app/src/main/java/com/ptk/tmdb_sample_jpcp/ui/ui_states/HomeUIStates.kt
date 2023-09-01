package com.ptk.tmdb_sample_jpcp.ui.ui_states

import com.ptk.tmdb_sample_jpcp.model.dto.MovieModel


data class HomeUIStates(

    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",
    val showAlertDialog: Boolean = false,

    val recommendList: ArrayList<MovieModel> = arrayListOf(),
    val nowPlayingList: ArrayList<MovieModel> = arrayListOf(),
    val upcomingList: ArrayList<MovieModel> = arrayListOf()

)