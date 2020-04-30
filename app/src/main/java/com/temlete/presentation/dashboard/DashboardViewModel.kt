package com.temlete.presentation.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.temlete.data.RxApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class DashboardViewModel(app: Application) : AndroidViewModel(app) {

    val aaa: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val bbb: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val rxApiClient = RxApiClient()
    private val disposable = CompositeDisposable()

    fun getRxDeRandomUser() {
        rxApiClient.screenApi.getRandomUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Log.d("hoge", "getRxDeRandomUser ${it.results[0].phone}")
                    aaa.postValue(it.results[0].phone)
                },
                onError = {
                    Log.d("hoge", "onError getRxDeRandomUser $it")
                }

            ).addTo(disposable)

        rxApiClient.screenApi.getRandomUser2()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Log.d("hoge", "getRxDeRandomUser2 ${it.results[0].email}")
                    bbb.postValue(it.results[0].email)
                },
                onError = {
                    Log.d("hoge", "onError getRxDeRandomUser2 $it")
                }

            ).addTo(disposable)
    }
}