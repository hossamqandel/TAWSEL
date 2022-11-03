package com.hossam.tawsel.core

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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

fun EditText.showInput(){
    this.transformationMethod = HideReturnsTransformationMethod.getInstance()
}

fun EditText.hideInput(){
    this.transformationMethod = PasswordTransformationMethod.getInstance()
}


fun EditText.onTextInputVisibilityChange(){

    when(this.transformationMethod){
        HideReturnsTransformationMethod.getInstance() -> { this.hideInput() }
        else -> { this.showInput() }
    }

}

fun List<View>.goneAll(){
    this.onEach {
        it.visibility = View.GONE
    }
}

fun List<View>.visibleAll(){
    this.onEach {
        it.visibility = View.VISIBLE
    }
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

fun Fragment.navDirection(directions: NavDirections){
    findNavController().navigate(directions)
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

//fun SwitchMaterial.changeTrackTint(colorId: Int){
//    this.trackTintList = ColorStateList.valueOf(resources.getColor(colorId))
//}

fun SwitchMaterial.changeTrackTint(resId: Int){
    try {
        this.trackTintList = ColorStateList.valueOf(resources.getColor(resId))
    } catch (e: Exception){
        Log.i(TAG, "changeTrackTint: $e")
        this.trackTintList = ColorStateList.valueOf(resId)
    }
}


fun View.setMargins(
    leftMarginDp: Int? = null,
    topMarginDp: Int? = null,
    rightMarginDp: Int? = null,
    bottomMarginDp: Int? = null
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        leftMarginDp?.run { params.leftMargin = this.dpToPx(context) }
        topMarginDp?.run { params.topMargin = this.dpToPx(context) }
        rightMarginDp?.run { params.rightMargin = this.dpToPx(context) }
        bottomMarginDp?.run { params.bottomMargin = this.dpToPx(context) }
        requestLayout()
    }
}

fun Int.dpToPx(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
}

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit){
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.collectLatest(collect)
        }
    }
}

//fun <T> Class<*>.LogI(value: T){
//    val TAG = this::class.java.simpleName
//
//    val functionName = object{}.javaClass.enclosingMethod?.name
//    Log.i(TAG, "$functionName --> ${value.toString()} ")
//}

//suspend fun onEmitHttpError(e: HttpException){
//     when (e.code()) {
//        401 ->   emit(Resource.Error("Un Authenticated"))
//        else ->  emit(Resource.Error(Const.Exception_MESSAGE_HTTP))
//     }
//
//}

