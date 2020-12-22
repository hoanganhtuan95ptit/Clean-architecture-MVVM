package com.tuanha.photo.di

import com.tuanha.photo.data.database.PhotoDb
import com.tuanha.photo.data.database.realm.PhotoDbImpl
import org.koin.dsl.module

@JvmField
val databaseModule = module {
    single<PhotoDb> { PhotoDbImpl() }
}