package com.tuanha.photo.data.database.realm

import com.tuanha.photo.data.database.PhotoDb
import com.tuanha.photo.data.database.realm.entities.RealmMapper
import com.tuanha.photo.data.database.realm.entities.RealmPhoto
import com.tuanha.photo.entities.Photo
import io.realm.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

open class PhotoDbImpl : PhotoDb {

    override fun clearAndSave(vararg photo: Photo) {
        val realm = Realm.getDefaultInstance()
        realm.use {
            realm.beginTransaction()
            realm.deleteAll()
            realm.insertOrUpdate(photo.map { RealmPhoto().toD(it) })
            realm.commitTransaction()
        }
    }

    override fun getAllFlow(): Flow<List<Photo>> = handlerAndReturn {
        it.where(RealmPhoto::class.java)
    }.map {
        it.map { it.toE(it) }
    }.flowOn(Dispatchers.IO)

    @OptIn(ExperimentalCoroutinesApi::class)
    protected fun <E, R> handlerAndReturn(block: (Realm) -> RealmQuery<R>): Flow<List<R>> where R : RealmObject, R : RealmMapper<E, R> = callbackFlow {
        val realm = Realm.getDefaultInstance()

        val listener = RealmChangeListener<RealmResults<R>> { t ->
            offer(realm.copyFromRealm(t))
        }

        val results = block(realm).findAllAsync()

        results.addChangeListener(listener)

        offer(realm.copyFromRealm(results))

        awaitClose {
            if (!realm.isClosed) {
                results.removeChangeListener(listener)
                realm.close()
            }
        }
    }.flowOn(Dispatchers.Main)
}