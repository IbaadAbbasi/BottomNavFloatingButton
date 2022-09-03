package com.pha.lahore.extension

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils

fun Context.isLocationEnabled(): Boolean {
    var locationMode = 0
    val locationProviders: String
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        try {
            locationMode =
                Settings.Secure.getInt(this.contentResolver, Settings.Secure.LOCATION_MODE)
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
        }
        locationMode != Settings.Secure.LOCATION_MODE_OFF
    } else {
        locationProviders =
            Settings.Secure.getString(this.contentResolver, Settings.Secure.LOCATION_MODE)
        !TextUtils.isEmpty(locationProviders)
    }
}