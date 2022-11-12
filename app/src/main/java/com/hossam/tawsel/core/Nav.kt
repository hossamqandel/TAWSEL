package com.hossam.tawsel.core

import com.hossam.tawsel.R

object Nav {

    val SPLASH_TO_LANGUAGE by lazy { R.id.action_splashFragment_to_languageFragment }
    val SPLASH_TO_HOME by lazy { R.id.action_splashFragment_to_driverHomeFragment }
    val SPLASH_TO_LOGIN by lazy { R.id.action_splashFragment_to_loginFragment }

    val LOGIN_TO_HOME by lazy { R.id.action_loginFragment_to_driverHomeFragment }
    val LOGIN_TO_FORGET_PASSWORD by lazy { R.id.action_loginFragment_to_resetPasswordFragment }
    val LANGUAGE_TO_LOGIN by lazy { R.id.action_languageFragment_to_loginFragment }


    val HOME_TO_NOTIFICATIONS by lazy { R.id.action_driverHomeFragment_to_notificationsFragment }
}