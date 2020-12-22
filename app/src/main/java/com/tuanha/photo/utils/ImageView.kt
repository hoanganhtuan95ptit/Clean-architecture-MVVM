package com.tuanha.photo.utils

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop


fun ImageView.load(source: Any, vararg transformations: Transformation<Bitmap>) = Glide.with(this)
    .asBitmap()
    .transform(
        if (transformations.isNullOrEmpty()) CenterCrop() else MultiTransformation<Bitmap>(
            *transformations
        )
    )
    .load(source)
    .into(this)