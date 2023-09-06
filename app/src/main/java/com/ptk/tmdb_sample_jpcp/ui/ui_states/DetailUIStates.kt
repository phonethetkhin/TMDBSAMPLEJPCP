package com.ptk.tmdb_sample_jpcp.ui.ui_states

import com.ptk.tmdb_sample_jpcp.model.dto.CastResponseModel
import com.ptk.tmdb_sample_jpcp.model.dto.MovieDetailResponseModel


data class DetailUIStates(

    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",
    val showAlertDialog: Boolean = false,

    val detailResponseModel: MovieDetailResponseModel? = null,
    val castModel: CastResponseModel? = null,

)