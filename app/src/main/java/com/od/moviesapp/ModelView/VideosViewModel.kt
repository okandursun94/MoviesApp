package com.od.moviesapp.ModelView

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.od.moviesapp.Api.ApiService
import com.od.moviesapp.Model.GetMoviesResponse
import com.od.moviesapp.Model.GetVideosResponse
import com.od.moviesapp.Model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class VideosViewModel: ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()
    val video = MutableLiveData<GetVideosResponse>()

    fun refresh(id: Int){
        fetchData(id)
    }

    private fun fetchData(id: Int){
        disposable.add(
            apiService.getMovieVideo(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<GetVideosResponse>(){

                    override fun onSuccess(value: GetVideosResponse?) {
                        if (value != null) {
                            video.value = value
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