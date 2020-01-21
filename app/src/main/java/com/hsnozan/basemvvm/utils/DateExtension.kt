package com.hsnozan.basemvvm.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun toSimpleString(date: Date) : String {
    val format = SimpleDateFormat("dd/MM/yyy")
    return format.format(date)
}
