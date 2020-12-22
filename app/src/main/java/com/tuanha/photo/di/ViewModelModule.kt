package com.tuanha.photo.di

import com.tuanha.photo.data.api.PhotoApi
import com.tuanha.photo.data.usecases.SyncPhotoUseCase
import com.tuanha.photo.ui.viewmodels.PhotoViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@JvmField
val viewModelModule = module {

    single { PhotoViewModel() }
}