package com.railway.ncs.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LoginResponse(
    @SerializedName("data")
    val `data`: Data? = Data(),
    @SerializedName("success")
    val success: Boolean? = false
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("createdAt")
        val createdAt: String? = "",
        @SerializedName("first_name")
        val firstName: String? = "",
        @SerializedName("id")
        val id: Int? = 0,
        @SerializedName("last_name")
        val lastName: String? = "",
        @SerializedName("middle_name")
        val middleName: String? = "",
        @SerializedName("password")
        val password: String? = "",
        @SerializedName("status")
        val status: Int? = 0,
        @SerializedName("updatedAt")
        val updatedAt: String? = "",
        @SerializedName("user_name")
        val userName: String? = ""
    ) : Parcelable
}