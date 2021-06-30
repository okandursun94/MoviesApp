package com.od.moviesapp.ModelView

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.od.moviesapp.Api.ApiService
import com.od.moviesapp.Model.GetMoviesResponse
import com.od.moviesapp.Model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel: ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()
    val movies = MutableLiveData<List<Movie>>()
    val movieLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        fetchData()
    }

    private fun fetchData(){
        loading.value = true
        disposable.add(
            apiService.getPopularMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<GetMoviesResponse>(){

                    override fun onSuccess(value: GetMoviesResponse?) {
                        if (value != null) {
                            movies.value = value.movies
                        }
                        movieLoadError.value = false
                        loading.value =false
                    }

                    override fun onError(e: Throwable?) {
                        if (e != null) {
                            println(e.message.toString())
                        }
                        movieLoadError.value = true
                        loading.value =false
                    }
                })
        )
    }

    fun getMovieDetail(id: Int){
        fetchDetail(id)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun fetchDetail(id: Int){
        loading.value = true
        disposable.add(
            apiService.getMovieDetail(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Movie>(){
                    override fun onSuccess(value: Movie?) {
                        if (value != null) {
                            //movies.value = value
                        }
                        movieLoadError.value = false
                        loading.value =false
                    }

                    override fun onError(e: Throwable?) {
                        if (e != null) {
                            println(e.message.toString())
                        }
                        movieLoadError.value = true
                        loading.value =false
                    }

                })
        )
    }

}