package com.pha.lahore.extension

import android.content.Context
import android.content.Intent
import android.view.View
import com.railway.ncs.activities.LoginActivity


fun Context.eChallanLogout(){
    val intent = Intent(this, LoginActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    this.startActivity(intent)
}
fun View.gone(){
    visibility = View.GONE
}