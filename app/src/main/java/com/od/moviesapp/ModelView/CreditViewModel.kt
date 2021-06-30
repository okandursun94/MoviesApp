package com.od.moviesapp.ModelView

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.od.moviesapp.Api.ApiService
import com.od.moviesapp.Model.Cast
import com.od.moviesapp.Model.GetCastResponse
import com.od.moviesapp.Model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CreditViewModel : ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()
    val cast = MutableLiveData<GetCastResponse>()

    fun refresh(query: Int){
        fetchData(query)
    }

    private fun fetchData(id: Int){
        disposable.add(
            apiService.getCredit(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<GetCastResponse>(){

                    override fun onSuccess(value: GetCastResponse?) {
                        if (value != null) {
                            cast.value = value
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