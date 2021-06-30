package com.od.moviesapp.ModelView

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.od.moviesapp.Api.ApiService
import com.od.moviesapp.Model.Movie
import com.od.moviesapp.Model.Person
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PersonViewModel : ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()
    val person = MutableLiveData<Person>()

    fun refresh(query: Int){
        fetchData(query)
    }

    private fun fetchData(id: Int){
        disposable.add(
            apiService.getPerson(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Person>(){

                    override fun onSuccess(value: Person?) {
                        if (value != null) {
                            person.value = value
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