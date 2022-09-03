package com.railway.ncs.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LoginRequest(


    @SerializedName("userId")
    var userId: String? = "",

    @SerializedName("password")
    var password: String? = ""


) : Parcelable