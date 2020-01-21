package com.hsnozan.basemvvm.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

open class SharedViewModel(app: Application) : AndroidViewModel(app) {

    val selected = MutableLiveData<String>()

    fun select(stringValue: String?) {
        selected.value = stringValue
    }
}