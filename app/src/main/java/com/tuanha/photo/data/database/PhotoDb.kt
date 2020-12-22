package com.tuanha.photo.data.database

import com.tuanha.photo.entities.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoDb {
    fun clearAndSave(vararg photo: Photo)

    fun getAllFlow(): Flow<List<Photo>>
}