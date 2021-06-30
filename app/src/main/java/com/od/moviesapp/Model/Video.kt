package com.od.moviesapp.Model

import com.google.gson.annotations.SerializedName

data class Video(

    @SerializedName("id") val id: String,
    @SerializedName("iso_639_1") val iso_639_1: String,
    @SerializedName("iso_3166_1") val iso_3166_1: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("site") val site: String,
    @SerializedName("size") val size: Int,
    @SerializedName("type") val type: String
) {
    val trailerImagePath: String // link for image
        get() = "https://img.youtube.com/vi/$key/hqdefault.jpg"
    val youtubeLink: String  // link for video
        get() = "https://www.youtube.com/watch?v=$key"
}