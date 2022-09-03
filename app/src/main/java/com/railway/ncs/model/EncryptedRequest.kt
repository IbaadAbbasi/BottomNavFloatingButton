package com.railway.ncs.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class EncryptedRequest(
    @SerializedName("cipher")
    var cipher: String? = ""
) : Parcelable