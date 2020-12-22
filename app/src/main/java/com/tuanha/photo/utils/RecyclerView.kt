package com.tuanha.photo.utils

import androidx.recyclerview.widget.RecyclerView

inline fun <reified A : RecyclerView.Adapter<*>> RecyclerView.getAdapter():A? = adapter as? A