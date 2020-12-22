package com.tuanha.photo.data.api.test

import com.tuanha.photo.data.api.PhotoApi
import com.tuanha.photo.entities.Photo

class PhotoApiImpl : PhotoApi {
    override suspend fun fetchPhoto(): List<Photo> {
        val list = arrayListOf<Photo>()
        for (i in 100..900) {
            list.add(Photo().apply { url = "https://picsum.photos/id/${i}/200/300" })
        }
        return list
    }

}