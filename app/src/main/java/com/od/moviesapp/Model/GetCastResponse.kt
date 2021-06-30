package com.od.moviesapp.Model

import com.google.gson.annotations.SerializedName

data class GetCastResponse (
    @SerializedName("id") val id: String,
    @SerializedName("cast") val cast: List <Cast>
)