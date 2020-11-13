package com.eneserdogan.keovecasekotlin.LogOutServices

import com.eneserdogan.keovecasekotlin.model.Message
import com.eneserdogan.keovecasekotlin.model.RefreshModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LogoutAPIService {
    @POST("api/user/revoke-refresh-token")
    fun logoutAuth(@Header("Authorization") authHeader:String,@Body refreshToken:RefreshModel): Call<Message>
}