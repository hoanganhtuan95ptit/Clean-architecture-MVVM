package com.tuanha.photo.di

import com.tuanha.photo.data.api.PhotoApi
import com.tuanha.photo.data.api.test.PhotoApiImpl
import org.koin.dsl.module

@JvmField
val apiModule = module {

    single<PhotoApi> { PhotoApiImpl() }
}