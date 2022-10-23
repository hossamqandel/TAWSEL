package com.hossam.tawsel.core

import android.content.Context
import android.content.SharedPreferences

object SharedPref {

    private var mAppContext: Context? = null
    private const val SHARED_PREFERENCES_NAME = "tawsel data"
    private const val USER_NAME = "name"
    private const val USER_PHONE = "phone"
    private const val USER_EMAIL = "email"
    private const val USER_ADDRESS = "address"
    private const val USER_LOGO = "logo"
    private const val USER_UPDATED_AT = "updated_at"
    private const val USER_CREATED_AT = "created_at"
    private const val USER_ID = "id"


    fun init(appContext: Context?) {
        mAppContext = appContext
    }

    private fun getSharedPreferences(): SharedPreferences {
        return mAppContext!!.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setUserName (value : String){
        val editor = getSharedPreferences().edit()
        editor.putString(USER_NAME, value).apply()
    }
    fun getUserName ():String = getSharedPreferences().getString(USER_NAME , "")!!


}