package com.od.moviesapp.Api

import com.od.moviesapp.Model.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String ,
        @Query("page") page: Int
    ): Single<GetMoviesResponse>

    @GET("search/movie")
    fun getMovie(
        @Query("api_key") apiKey: String ,
        @Query("query") query: String
    ): Single<GetMoviesResponse>

    @GET()
    fun getMovieDetail(
        @Url() id: String,
        @Query("api_key") apiKey: String
    ): Single<Movie>

    @GET("movie/{id}/credits")
    fun getCredit(
        @Path("id") id: String ,
        @Query("api_key") apiKey: String
    ): Single<GetCastResponse>

    @GET("movie/{id}/videos")
    fun getMovieVideo(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): Single<GetVideosResponse>

    @GET("person/{id}")
    fun getPerson(
        @Path("id") personId: String,
        @Query("api_key") apiKey: String
    ): Single<Person>

    @GET("person/{id}/movie_credits")
    fun getPersonMovieCredit(
        @Path("id") personId: String,
        @Query("api_key") apiKey: String
    ): Single<NetworkPersonMovieCredit>

    @GET("person/{id}/tv_credits")
    fun getPersonSeriesCredit(
        @Path("id") personId: String,
        @Query("api_key") apiKey: String
    ): Single<NetworkPersonTvCredit>

}