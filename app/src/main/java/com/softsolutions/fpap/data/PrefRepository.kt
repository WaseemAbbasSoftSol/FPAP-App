package com.softsolutions.fpap.data

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.softsolutions.fpap.model.account.User
import com.softsolutions.fpap.utils.APP_TAG
import com.softsolutions.fpap.utils.KEY_USER_MEDIUM
import com.softsolutions.fpap.utils.KEY_USER_OBJECT

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

    fun saveUser(user:User){
        val userJson= Gson().toJson(user)
        prefs.edit().putString(KEY_USER_OBJECT, userJson).apply()
    }

    fun getUser():User?{
        val user:User
        val gson=Gson()
        val userJson=prefs.getString(KEY_USER_OBJECT, null)
        return try {
            user = gson.fromJson<User>(userJson, User::class.java)
            user
        } catch (e: Exception) {
            null
        }
    }
    fun deleteUserFromPref()=prefs.edit().remove(KEY_USER_OBJECT).apply()

}