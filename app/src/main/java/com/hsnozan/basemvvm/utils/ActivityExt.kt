package com.hsnozan.basemvvm.utils

import android.app.Activity
import android.content.ClipData.newIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hsnozan.basemvvm.R

fun Activity.toast(message : CharSequence, duration : Int = Toast.LENGTH_LONG) =
    Toast.makeText(this,message,duration).show()

fun Activity.hideKeyboardFrom() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus
    if (view == null) { view = View(this) }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

inline fun Activity.alertDialog(body : AlertDialog.Builder.() -> AlertDialog.Builder) : AlertDialog {
    return AlertDialog.Builder(this)
        .body()
        .show()
}

inline fun <reified T : Any> AppCompatActivity.launchActivity(
    requestCode: Int = -1,
    hasAnimation: Boolean = false,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)