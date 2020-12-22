package com.tuanha.photo.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tuanha.photo.data.repositories.PhotoRepository
import com.tuanha.photo.data.usecases.GetPhotoLiveUseCase
import com.tuanha.photo.entities.Photo
import kotlinx.coroutines.flow.collect
import org.koin.core.KoinComponent
import org.koin.core.inject

class PhotoViewModel : ViewModel(), KoinComponent {

    private val photoLiveUseCase by inject<GetPhotoLiveUseCase>()

    val photo: LiveData<List<Photo>> = liveData {
        photoLiveUseCase.invoke().collect {
            emit(it)
        }
    }

}