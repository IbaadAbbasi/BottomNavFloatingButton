package com.railway.ncs

import android.app.Application
import android.content.Context
import com.android.print.sdk.util.Utils
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseClass : Application() {
    override fun onCreate() {
        super.onCreate()
        BaseClass.appContext = applicationContext
        initPrintSDK()
    }

    companion object {
        lateinit  var appContext: Context
    }

    private fun initPrintSDK() {
        try {
            val pro = Utils.getBtConnInfo(this)
            if (pro.isEmpty) {
                pro["mac"] = ""
            }
        } catch (e: Exception) {
            Utils.saveBtConnInfo(this, "")
        }
    }
}