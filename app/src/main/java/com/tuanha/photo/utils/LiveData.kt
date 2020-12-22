package com.tuanha.photo.utils

import android.annotation.SuppressLint
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.*

@AnyThread
fun <T> LiveData<T>?.postValue(t: T) {
    when (this) {
        is MediatorLiveData<T> -> this.postValue(t)
        is MutableLiveData<T> -> this.postValue(t)
    }
}

fun <Y, X> LiveData<Map<Y, X>>.getOrEmpty(): Map<Y, X> {
    return getOrDefault(emptyMap())
}

fun <X> LiveData<List<X>>.getOrEmpty(): List<X> {
    return getOrDefault(emptyList())
}

fun <X> LiveData<X>.getOrDefault(default: X): X {
    return value ?: default
}

fun <X> LiveData<X>.getOrNull(): X? {
    return value
}

@MainThread
fun <T> LiveData<T>.combineSources(sources: List<LiveData<*>>, onChanged: MediatorLiveData<T>.() -> Unit = { postValue(null) }): LiveData<T> {
    return combineSources(*sources.toTypedArray()) {
        onChanged.invoke(this)
    }
}

@MainThread
fun <T> LiveData<T>.combineSources(vararg sources: LiveData<*>, onChanged: MediatorLiveData<T>.() -> Unit = { postValue(null) }): LiveData<T> {
    return addSources(*sources) {
        sources.map {
            it.value == null
        }.takeIf {
            !it.any { it }
        }?.let {
            onChanged(this)
        }
    }
}

@MainThread
fun <T> LiveData<T>.addSources(sources: List<LiveData<*>>, onChanged: MediatorLiveData<T>.() -> Unit = { postValue(null) }): LiveData<T> {
    return addSources(*sources.toTypedArray()) {
        onChanged.invoke(this)
    }
}

@MainThread
fun <T> LiveData<T>.addSources(vararg sources: LiveData<*>, onChanged: MediatorLiveData<T>.() -> Unit = { postValue(null) }): LiveData<T> {
    sources.forEach { item ->
        addSource(item) {
            onChanged(this)
        }
    }
    return this
}

@MainThread
fun <T, S> LiveData<T>.addSource(source: LiveData<S>, onChanged: MediatorLiveData<T>.(S) -> Unit = { postValue(null) }): LiveData<T> {
    (this as? MediatorLiveData<T>)?.addSource(source) {
        onChanged(this, it)
    }
    return this
}

@MainThread
fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, func: (T) -> (Unit)) {
    removeObservers(owner)
    observe(owner, Observer<T> { t -> func(t) })
}

@MainThread
@SuppressLint("NullSafeMutableLiveData")
fun <X> LiveData<X>.breakFirst(): LiveData<X> {
    val outputLiveData = MediatorLiveData<X>()
    outputLiveData.addSource(this, object : Observer<X> {
        var mFirstTime = true
        override fun onChanged(currentValue: X) {
            if (!mFirstTime && currentValue != null) {
                outputLiveData.value = currentValue
            }
            mFirstTime = false
        }
    })
    return outputLiveData
}

