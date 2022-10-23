package com.hossam.tawsel.core

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hossam.tawsel.R

private val TAG = "Extension"

fun handler(timer: Long, block: () -> Unit){
    Handler(Looper.getMainLooper()).postDelayed({
        block()
    }, timer)
}


infix fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChanged(text.toString()) }

        override fun afterTextChanged(p0: Editable?) {}
    })
}


fun View.onClick(block: () -> Unit){
    this.setOnClickListener {
        block()
    }
}

fun View.showSnackBar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.navigate(navId: Int){
    findNavController().navigate(navId)
}


fun currentThread(): String {
    return Thread.currentThread().toString()
}

fun Fragment.changeStatusBarIconsColor(colorId: Int){
    requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    requireActivity().window.statusBarColor = colorId
}

fun Fragment.changeStatusBarColor(colorId: Int){
    try {
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = resources.getColor(colorId)
    }catch (e: Resources.NotFoundException){
        Log.i(TAG, "changeStatusBarColor: $e")
    } catch (e: NullPointerException){
        Log.i(TAG, "changeStatusBarColor: $e")
    }
}

fun Fragment.setupStatusBarWithIcons(statusColor: Int, iconColor: Int){
    changeStatusBarColor(statusColor)
    changeStatusBarIconsColor(iconColor)
}