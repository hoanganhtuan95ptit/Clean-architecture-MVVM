package com.tuanha.photo.data.repositories

import com.tuanha.photo.data.api.PhotoApi
import com.tuanha.photo.data.database.PhotoDb
import com.tuanha.photo.entities.Photo
import kotlinx.coroutines.flow.Flow
import org.koin.core.inject

class PhotoRepositoryImpl : PhotoRepository {

    private val photoDb by inject<PhotoDb>()

    private val photoApi by inject<PhotoApi>()

    override suspend fun fetchPhoto(): List<Photo> {
        return photoApi.fetchPhoto()
    }

    override fun clearAndSave(vararg photo: Photo) {
        photoDb.clearAndSave(*photo)
    }

    override fun getPhotoFlow(): Flow<List<Photo>> {
        return photoDb.getAllFlow()
    }
}