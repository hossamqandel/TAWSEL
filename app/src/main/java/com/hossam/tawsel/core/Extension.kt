package com.hossam.tawsel.core

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

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