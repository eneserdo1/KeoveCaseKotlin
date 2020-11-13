package com.eneserdogan.keovecasekotlin.model

import com.google.gson.annotations.SerializedName

data class LoginInfo(
    @SerializedName("id")
    val id:Int?,
    @SerializedName("name")
    val name:String?,
    @SerializedName("surname")
    val surname:String?,
    @SerializedName("username")
    val username:String?,
    @SerializedName("jwtToken")
    val jwtToken:String?,
    @SerializedName("refreshToken")
    val refreshToken:String?
)