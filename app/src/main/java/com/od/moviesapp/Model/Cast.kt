package com.od.moviesapp.Model

import com.google.gson.annotations.SerializedName

data class Cast (
    @SerializedName("id") val id: String,
    @SerializedName("profile_path") val profile_path: String,
    @SerializedName("name") val name: String,
    @SerializedName("known_for_department") val known_for_department: String,
    @SerializedName("character") val character: String,
    @SerializedName("credit_id") val credit_id: String,
    @SerializedName("popularity") val popularity: String,
    @SerializedName("poster_path") val poster_path: String
)