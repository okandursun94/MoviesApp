package com.od.moviesapp.Model

import com.google.gson.annotations.SerializedName

data class NetworkPersonTvCredit(

    @SerializedName("cast") val cast: List<Cast>,
    //@SerializedName("crew") val crew: List<Crew>,
    @SerializedName("id") val id: Int
)