package com.tuanha.photo.ui.viewmodels

import androidx.lifecycle.*
import com.tuanha.photo.data.repositories.PhotoRepository
import com.tuanha.photo.data.usecases.GetPhotoLiveUseCase
import com.tuanha.photo.data.usecases.SyncPhotoUseCase
import com.tuanha.photo.entities.Photo
import com.tuanha.photo.ui.adapters.PhotoViewItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class PhotoViewModel : BaseViewModel(), KoinComponent {

    private val syncPhotoUseCase by inject<SyncPhotoUseCase>()

    private val photoLiveUseCase by inject<GetPhotoLiveUseCase>()

    private val photo: LiveData<List<Photo>> = liveData {
        photoLiveUseCase.invoke().collect {
            emit(it)
        }
    }

    val photoViewItem :LiveData<List<PhotoViewItem>> = photo.switchMapLiveDataEmit {
        it.map {
            PhotoViewItem(it)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            syncPhotoUseCase.invoke()
        }
    }
}