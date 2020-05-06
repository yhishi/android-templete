package com.temlete.helper

import android.content.Context
import android.content.SharedPreferences
import com.securepreferences.SecurePreferences

/** SharedPreferencesを扱うクラス
 *  新しく変数を使う場合、[Prefs]にgetter/setterを記述する
 **/
class Prefs(context: Context) {
    private val prefs: SharedPreferences = SecurePreferences(context)

    var isFinishTutorial: Boolean
        get() = prefs.getBoolean(IS_FINISH_TUTORIAL, false)
        set(value) = prefs.edit().putBoolean(IS_FINISH_TUTORIAL, value).apply()

    companion object {
        const val IS_FINISH_TUTORIAL = "isFinishTutorial"
    }
}