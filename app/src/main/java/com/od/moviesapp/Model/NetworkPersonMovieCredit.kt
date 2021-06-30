package com.od.moviesapp.Model

import com.google.gson.annotations.SerializedName

data class NetworkPersonMovieCredit(

    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<Cast>,
    //@SerializedName("crew") val crew: List<MovieCrew>   // there was no need to crew
)