package com.hsnozan.basemvvm.utils

import android.os.Build
import android.util.Log
import java.lang.Exception

/** Any Extantion */

inline fun supportVersion(ver : Int , func : () -> Unit){
    if (Build.VERSION.SDK_INT >= ver){
        func.invoke()
    }
}

fun Any.tryCatch(tryBlock : () -> Unit ,
                 catchBlock : ((t:Throwable) -> Unit)? = null,
                 finalBlock : (() -> Unit)? = null){
    try {
        tryBlock()
    }catch (ex : Exception){
        catchBlock?.invoke(ex)
    }finally {
        finalBlock?.invoke()
    }
}

fun Any.logE(message: String) = Log.e(this::class.java.simpleName, message)

fun Any.logD(message: String) = Log.d(this::class.java.simpleName, message)

fun Any.logV(message: String) = Log.v(this::class.java.simpleName, message)

fun Any.logW(message: String) = Log.w(this::class.java.simpleName, message)

fun Any.logI(message: String) = Log.i(this::class.java.simpleName, message)


