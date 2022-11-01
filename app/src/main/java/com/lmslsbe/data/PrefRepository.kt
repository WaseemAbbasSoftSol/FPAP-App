package com.lmslsbe.data

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.lmslsbe.model.account.User
import com.lmslsbe.utils.APP_TAG
import com.lmslsbe.utils.KEY_COUNTRY_CODE_NAME
import com.lmslsbe.utils.KEY_USER_MEDIUM
import com.lmslsbe.utils.KEY_USER_OBJECT

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

    fun saveUser(user: User){
        val userJson= Gson().toJson(user)
        prefs.edit().putString(KEY_USER_OBJECT, userJson).apply()
    }

    fun getUser(): User?{
        val user: User
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

    fun saveCountryCodeName(code: String){
        val codeName= Gson().toJson(code)
        prefs.edit().putString(KEY_COUNTRY_CODE_NAME,codeName).apply()
    }
    fun getCountryCodeNameFromPref(): String? {
        val code:String
        val gson = Gson()
        val userJson = prefs.getString(KEY_COUNTRY_CODE_NAME, null)
        return try {
            code = gson.fromJson<String>(userJson, String::class.java)
            code
        } catch (e: Exception) {
            null
        }
    }
}