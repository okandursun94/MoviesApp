package com.od.moviesapp.ModelView

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.od.moviesapp.Api.ApiService
import com.od.moviesapp.Model.Cast
import com.od.moviesapp.Model.NetworkPersonTvCredit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PersonSeriesViewModel: ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()
    val cast = MutableLiveData<List<Cast>>()

    fun refresh(id: Int){
        fetchData(id)
    }

    private fun fetchData(id: Int){
        disposable.add(
            apiService.getPersonSeriesCredit(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<NetworkPersonTvCredit>(){
                    override fun onSuccess(value: NetworkPersonTvCredit?) {
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