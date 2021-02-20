package com.gilly.gifsearch.core.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.itsdcode.ncodeapp.data.Failure

fun <T: Any, R: LiveData<T>> LifecycleOwner.observe(liveData: R, observer: (T?) -> Unit) {
    liveData.observe(this, observer)
}

fun <T: Failure, R: LiveData<T>> LifecycleOwner.failure(liveData: R, observer: (T?) -> Unit) {
    liveData.observe(this, observer)
}