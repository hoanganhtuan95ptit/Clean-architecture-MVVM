package com.tuanha.photo.utils

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.setGone(gone: Boolean) {
    this.visibility = if (gone) View.GONE else View.VISIBLE
}

fun View.setVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.setInvisible(isInvisible: Boolean) {
    this.visibility = if (isInvisible) View.INVISIBLE else View.VISIBLE
}

fun View.isGone() = visibility == View.GONE

fun View.isVisible() = visibility == View.VISIBLE

fun View.isInVisible() = visibility == View.INVISIBLE

fun View.resize(width: Int = -3, height: Int = -3, weight: Float = 0f) {
    var update = false

    val params = layoutParams as ViewGroup.LayoutParams

    if (params.width != width && width >= -2) {
        params.width = width
        update = true
    }

    if (params is LinearLayout.LayoutParams && params.weight != weight) {
        params.weight = weight
        update = true
    }

    if (params.height != height && height >= -2) {
        params.height = height
        update = true
    }
    if (update) {
        layoutParams = layoutParams
    }
}
