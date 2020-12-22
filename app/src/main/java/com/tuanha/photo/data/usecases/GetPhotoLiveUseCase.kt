package com.tuanha.photo.data.usecases

import android.util.Log
import com.tuanha.photo.data.repositories.PhotoRepository
import com.tuanha.photo.entities.Photo
import kotlinx.coroutines.flow.Flow
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetPhotoLiveUseCase : KoinComponent {

    private val photoRepository by inject<PhotoRepository>()

    suspend fun invoke(param: Param? = null): Flow<List<Photo>> {
        return photoRepository.getPhotoFlow()
    }

    class Param() {}
}