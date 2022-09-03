package com.pha.lahore.extension.utils

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkAvailable():Boolean{
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}