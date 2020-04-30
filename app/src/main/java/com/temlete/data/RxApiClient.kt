package com.temlete.data

import com.temlete.data.network.ScreenApi

class RxApiClient : ApiClient() {
    override val baseUrl: String = "https://randomuser.me/"

    /** screenApi 画面 or 機能ごとのAPIクラスをここに追加していく */
    val screenApi: ScreenApi = retrofit.create(ScreenApi::class.java)
}