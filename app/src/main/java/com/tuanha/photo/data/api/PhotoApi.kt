package com.tuanha.photo.data.api

import com.tuanha.photo.entities.Photo
import io.realm.internal.Table
import retrofit2.http.GET

interface PhotoApi {

    @GET("photos")
    suspend fun fetchPhoto(): List<Photo>
}