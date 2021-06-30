package com.od.moviesapp.Model

import com.google.gson.annotations.SerializedName

data class GetVideosResponse (
    @SerializedName("id") val id: String,
    @SerializedName("results") val video: List <Video>
)