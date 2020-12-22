package com.tuanha.photo.data.database.realm.entities

import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmModel

interface RealmMapper<E, D> where D : RealmModel, D : RealmMapper<E, D> {

    fun toD(type: E): D

    fun toE(type: D): E

}


