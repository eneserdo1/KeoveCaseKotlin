package com.eneserdogan.keovecasekotlin.RefreshTokenServices

import com.eneserdogan.keovecasekotlin.model.LoginInfo
import com.eneserdogan.keovecasekotlin.model.RefreshModel
import com.eneserdogan.keovecasekotlin.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshAPIService {
    @POST("api/user/refresh-token")
    fun refreshPostData(@Body refreshToken:RefreshModel): Call<LoginInfo>

}