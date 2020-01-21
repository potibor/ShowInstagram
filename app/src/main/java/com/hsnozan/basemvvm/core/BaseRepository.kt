package com.hsnozan.basemvvm.core

import androidx.lifecycle.MutableLiveData
import com.hsnozan.basemvvm.model.Resources

interface BaseRepository<T>{
    val genericLiveData : MutableLiveData<Resources<T>>
}