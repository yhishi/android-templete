package com.temlete.config

import android.app.Application
import com.temlete.helper.Prefs

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // SharedPreferences用クラスの初期化
        prefs = Prefs(this)
    }

    companion object {
        lateinit var prefs: Prefs
    }
}