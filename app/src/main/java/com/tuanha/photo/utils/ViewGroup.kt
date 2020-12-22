package com.tuanha.photo.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes l: Int): View {
    return LayoutInflater.from(context).inflate(l, this, false)
}