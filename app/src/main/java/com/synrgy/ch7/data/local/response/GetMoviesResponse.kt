package com.synrgy.ch7.data.local.response

import com.google.gson.annotations.SerializedName
import com.synrgy.ch7.data.local.model.Movie

data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)