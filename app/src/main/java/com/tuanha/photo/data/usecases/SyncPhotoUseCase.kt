package com.tuanha.photo.data.usecases

import com.tuanha.photo.data.repositories.PhotoRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class SyncPhotoUseCase : KoinComponent {

    private val photoRepository by inject<PhotoRepository>()

    suspend fun invoke(param: Param? = null) {
        photoRepository.clearAndSave(*photoRepository.fetchPhoto().toTypedArray())
    }

    class Param() {}
}