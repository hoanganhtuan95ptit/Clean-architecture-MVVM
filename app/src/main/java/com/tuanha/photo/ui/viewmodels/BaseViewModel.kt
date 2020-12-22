package com.tuanha.photo.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext
import kotlin.experimental.ExperimentalTypeInference

abstract class BaseViewModel : ViewModel , KoinComponent{

    companion object {
        private const val TAG = "BaseViewModel"
    }

    constructor() : super() {
    }

    val handler = CoroutineExceptionHandler { _: CoroutineContext, throwable: Throwable ->
    }

    override fun onCleared() {
        super.onCleared()
    }

    @OptIn(ExperimentalTypeInference::class)
    open fun <Y> liveDataEmit(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        @BuilderInference block: suspend () -> Y
    ): LiveData<Y> = liveData(handler + dispatcher) {
        emit(block())
    }

    @OptIn(ExperimentalTypeInference::class)
    fun <Y> liveData(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        @BuilderInference block: suspend LiveDataScope<Y>.() -> Unit
    ): LiveData<Y> = liveData(handler + dispatcher) {
        block()
    }

    open fun <X, Y> LiveData<X>.switchMapLiveDataEmit(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend (X) -> Y
    ): LiveData<Y> = switchMap {
        liveData(handler + dispatcher) {
            emit(block(it))
        }
    }

    fun <X, Y> LiveData<X>.switchMapLiveData(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend LiveDataScope<Y>.(X) -> Unit
    ): LiveData<Y> = switchMap {
        liveData<Y>(handler + dispatcher) {
            block(this@liveData, it)
        }
    }

}
