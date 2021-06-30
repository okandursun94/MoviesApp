package com.od.moviesapp.Api

import com.od.moviesapp.Model.*
import com.od.moviesapp.util.Constants
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private val api: ApiInterface

    init{
        api = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    fun getPopularMovies(): Single<GetMoviesResponse> {
        return api.getPopularMovies(Constants.API_KEY,page = 1)
    }

    fun getMovie(query: String): Single<GetMoviesResponse> {
        return api.getMovie(Constants.API_KEY,query)
    }

    fun getMovieDetail(id: Int): Single<Movie> {
        val  c = "movie/"+id
        return api.getMovieDetail(c, Constants.API_KEY)
    }

    fun getCredit(id: Int): Single<GetCastResponse> {
        return api.getCredit(id.toString(), Constants.API_KEY)
    }

    fun getMovieVideo(id: Int): Single<GetVideosResponse> {
        return api.getMovieVideo(id.toString(), Constants.API_KEY)
    }

    fun getPerson(id: Int): Single<Person> {
        return api.getPerson(id.toString(), Constants.API_KEY)
    }

    fun getPersonMovieCredit(id: Int): Single<NetworkPersonMovieCredit> {
        return api.getPersonMovieCredit(id.toString(), Constants.API_KEY)
    }
    fun getPersonSeriesCredit(id: Int): Single<NetworkPersonTvCredit> {
        return api.getPersonSeriesCredit(id.toString(), Constants.API_KEY)
    }

}