package com.eneserdogan.keovecasekotlin.RefreshTokenServices

import com.eneserdogan.keovecasekotlin.model.LoginInfo
import com.eneserdogan.keovecasekotlin.model.RefreshModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiRefresh {
    fun getnewToken(refreshToken:RefreshModel, onResult: (LoginInfo?) -> Unit){
        val retrofit =ServiceBuilderRefresh.buildRefreshService(RefreshAPIService::class.java)
        retrofit.refreshPostData(refreshToken).enqueue(
            object :Callback<LoginInfo>{
                override fun onFailure(call: Call<LoginInfo>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<LoginInfo>, response: Response<LoginInfo>) {
                    val loginData=response.body()
                    println("restapi response "+loginData?.jwtToken)
                    onResult(loginData)
                }

            }
        )
    }
}