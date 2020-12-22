package com.tuanha.photo.data.database.realm

import com.tuanha.photo.data.database.PhotoDb
import com.tuanha.photo.entities.Photo
import kotlinx.coroutines.flow.Flow

class PhotoDbImpl :PhotoDb {

    override fun clearAndSave(vararg photo: Photo) {

    }

    override fun getAllFlow(): Flow<Photo> {
        TODO("Not yet implemented")
    }
}