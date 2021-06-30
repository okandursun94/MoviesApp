package com.od.moviesapp.ModelView

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.od.moviesapp.Api.ApiService
import com.od.moviesapp.Model.GetMoviesResponse
import com.od.moviesapp.Model.Movie
import com.od.moviesapp.View.Adapter.MovieListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SearchViewModel: ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()

    val movies = MutableLiveData<List<Movie>>()
    val movieLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(query: String,adapter: MovieListAdapter){
        fetchData(query,adapter)
    }

    private fun fetchData(query: String, adapter: MovieListAdapter){
        loading.value = true
        disposable.add(
            apiService.getMovie(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<GetMoviesResponse>(){

                    override fun onSuccess(value: GetMoviesResponse?) {
                        if (value != null) {
                            movies.value = value.movies
                            adapter.updateMovies(movies.value!!)
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

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}