package com.tuanha.photo.data.database.realm.entities

import com.tuanha.photo.entities.Photo
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmPhoto : RealmObject(), RealmMapper<Photo, RealmPhoto> {
    override fun toD(type: Photo): RealmPhoto = this.apply {
        url = type.url
    }

    override fun toE(type: RealmPhoto): Photo = Photo().apply {
        url = type.url
    }

    @PrimaryKey
    var url: String = ""
}