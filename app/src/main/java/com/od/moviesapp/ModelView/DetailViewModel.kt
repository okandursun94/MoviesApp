package com.od.moviesapp.ModelView

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.od.moviesapp.Api.ApiService
import com.od.moviesapp.Model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailViewModel: ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()
    val movie = MutableLiveData<Movie>()
    val movieLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(query: Int){
        fetchData(query)
    }

    private fun fetchData(id: Int){
        disposable.add(
            apiService.getMovieDetail(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Movie>(){

                    override fun onSuccess(value: Movie?) {
                        if (value != null) {
                            movie.value = value
                        }
                    }

                    override fun onError(e: Throwable?) {
                        if (e != null) {
                            println(e.message.toString())
                        }
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}