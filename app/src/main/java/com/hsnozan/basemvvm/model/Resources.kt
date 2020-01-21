package com.hsnozan.basemvvm.model

sealed class Resources<out T>{
    class Success<out T>(val data: T?) : Resources<T>()
    class Error<out T>(val errorMessage: String? = null) : Resources<T>()
    class Loading<out T> : Resources<T>()
}