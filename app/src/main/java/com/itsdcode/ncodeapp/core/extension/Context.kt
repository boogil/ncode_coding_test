package com.gilly.gifsearch.core.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.Gravity
import android.widget.Toast

val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

val Context.appName: String get() =  applicationInfo.loadLabel(packageManager).toString()

fun Context.makeToast(strResourceId: Int) {
    Toast.makeText(this, strResourceId, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.CENTER, 0, 0)
    }.show()
}