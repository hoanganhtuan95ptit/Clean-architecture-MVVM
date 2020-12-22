package com.tuanha.photo.di

import com.tuanha.photo.data.usecases.GetPhotoLiveUseCase
import com.tuanha.photo.data.usecases.SyncPhotoUseCase
import org.koin.dsl.module

@JvmField
val useCaseModule = module {

    single { SyncPhotoUseCase() }

    single { GetPhotoLiveUseCase() }
}