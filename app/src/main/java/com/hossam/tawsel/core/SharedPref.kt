package com.hossam.tawsel.core

import android.content.Context
import android.content.SharedPreferences

object SharedPref {

    private var mAppContext: Context? = null
    private const val SHARED_PREFERENCES_NAME = "tawsel data"
    private const val USER_PHONE = "mobile"


    fun init(appContext: Context?) {
        mAppContext = appContext
    }

    private fun getSharedPreferences(): SharedPreferences {
        return mAppContext!!.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setUserPhone (value : String){
        val editor = getSharedPreferences().edit()
        editor.putString(USER_PHONE, value).apply()
    }

    fun getUserPhone ():String = getSharedPreferences().getString(USER_PHONE , "")!!
}