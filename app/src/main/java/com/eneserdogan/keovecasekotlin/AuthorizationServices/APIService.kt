package com.eneserdogan.keovecasekotlin.AuthorizationServices

import com.eneserdogan.keovecasekotlin.model.LoginInfo
import com.eneserdogan.keovecasekotlin.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("api/user/authenticate")
    fun postData(@Body user: User):Call<LoginInfo>
}