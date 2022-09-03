package com.pha.lahore.extension

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager

fun Context.isMobileDataEnabled(): Boolean? {

    val connectivityService = getSystemService(CONNECTIVITY_SERVICE)
    val cm = connectivityService as ConnectivityManager
    return try {
        val c = Class.forName(cm.javaClass.name)
        val m = c.getDeclaredMethod("getMobileDataEnabled")
        m.isAccessible = true
        m.invoke(cm) as Boolean
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}