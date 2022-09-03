package com.pha.lahore.extension

import android.app.AlertDialog
import android.content.Context

fun Context.alertDialog(msg: String) {
    val error_401 = AlertDialog.Builder(this)
    error_401.setMessage(msg).setPositiveButton(
        "OK"
    ) { dialog, which -> }
    error_401.show()
}