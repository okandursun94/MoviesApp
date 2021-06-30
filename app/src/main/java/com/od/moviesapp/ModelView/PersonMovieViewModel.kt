package com.od.moviesapp.ModelView

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.od.moviesapp.Api.ApiService
import com.od.moviesapp.Model.Cast
import com.od.moviesapp.Model.GetMoviesResponse
import com.od.moviesapp.Model.Movie
import com.od.moviesapp.Model.NetworkPersonMovieCredit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PersonMovieViewModel: ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()
    val cast = MutableLiveData<List<Cast>>()

    fun refresh(id: Int){
        fetchData(id)
    }

    private fun fetchData(id: Int){
        disposable.add(
            apiService.getPersonMovieCredit(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<NetworkPersonMovieCredit>(){
                    override fun onSuccess(value: NetworkPersonMovieCredit?) {
                        if (value != null) {
                            cast.value = value.cast
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