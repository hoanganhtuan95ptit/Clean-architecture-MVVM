package com.tuanha.photo.data.repositories

import com.tuanha.photo.entities.Photo
import kotlinx.coroutines.flow.Flow
import org.koin.core.KoinComponent

interface PhotoRepository : KoinComponent {
    suspend fun fetchPhoto(): List<Photo>

    fun clearAndSave(vararg photo: Photo)

    fun getPhotoFlow(): Flow<List<Photo>>
}