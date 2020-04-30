package com.temlete.presentation.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.temlete.data.RxApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class DashboardViewModel(app: Application) : AndroidViewModel(app) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val rxApiClient = RxApiClient()
    private val disposable = CompositeDisposable()

    fun getRxDeRandomUser() {
        rxApiClient.screenApi.getRandomUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Log.d("hoge", "getRxDeRandomUser ${it.results[0].gender}")
                    _text.value = it.results[0].phone
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
                    _text.value = it.results[0].phone
                },
                onError = {
                    Log.d("hoge", "onError getRxDeRandomUser2 $it")
                }

            ).addTo(disposable)
    }
}