package com.gilly.gifsearch.core.extension

import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jakewharton.rxbinding4.view.clicks
import java.util.concurrent.TimeUnit

fun ImageView.loadUrl(url: String, placeHolderResourceId: Int? = null, dontTransform: Boolean = false) {
    val requestBuilder = Glide.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())

    placeHolderResourceId?.let {
//        requestBuilder.placeholder(Glide.with(context).load(placeHolderResourceId))
        requestBuilder.thumbnail(Glide.with(context).load(placeHolderResourceId))
    }
    if (dontTransform) {
        requestBuilder.dontTransform()
    }

    requestBuilder.into(this)
}

fun ImageView.loadResource(resourceId: Int, dontTransform: Boolean = false) {
    val requestBuilder = Glide.with(context)
        .load(resourceId)
        .transition(DrawableTransitionOptions.withCrossFade())

    if (dontTransform) {
        requestBuilder.dontTransform()
    }

    requestBuilder.into(this)
}

/**
 * RxBinding의 Throttle 기능 사용하는 Button 함수
 * @param throttleSecond 해당 시간동안 중복 클릭 방지 (기본으로 1초)
 * @param subscribe 클릭 리스너 정의
 */
fun Button.onThrottleClick(throttleSecond: Long = 1, subscribe: (() -> Unit)? = null) = clicks()
    .throttleFirst(throttleSecond, TimeUnit.SECONDS)
    .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
    .subscribe { subscribe?.invoke() }
fun ConstraintLayout.onThrottleClick(throttleSecond: Long = 1, subscribe: (() -> Unit)? = null) = clicks()
    .throttleFirst(throttleSecond, TimeUnit.SECONDS)
    .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
    .subscribe { subscribe?.invoke() }
fun ImageView.onThrottleClick(throttleSecond: Long = 1, subscribe: (() -> Unit)? = null) = clicks()
    .throttleFirst(throttleSecond, TimeUnit.SECONDS)
    .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
    .subscribe { subscribe?.invoke() }
fun LinearLayoutCompat.onThrottleClick(throttleSecond: Long = 1, subscribe: (() -> Unit)? = null) = clicks()
    .throttleFirst(throttleSecond, TimeUnit.SECONDS)
    .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
    .subscribe { subscribe?.invoke() }
