package com.softsolutions.fpap.data

import android.app.Application
import android.content.Context
import com.softsolutions.fpap.utils.APP_TAG
import com.softsolutions.fpap.utils.KEY_USER_MEDIUM

class PrefRepository(private val app: Application) {
    private val prefs = app.getSharedPreferences(
        APP_TAG, Context.MODE_PRIVATE
    )

    fun saveMedium(medium: String){
        prefs.edit().putString(KEY_USER_MEDIUM,medium).apply()
    }

    fun getMedium():String?{
        return prefs.getString(KEY_USER_MEDIUM,"")
    }

}