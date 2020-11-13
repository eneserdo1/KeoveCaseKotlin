package com.eneserdogan.keovecasekotlin.LogOutServices

import com.eneserdogan.keovecasekotlin.model.Message
import com.eneserdogan.keovecasekotlin.model.RefreshModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiLogout {
    fun invokeToken(refreshToken: RefreshModel,jwtToken:String, onResult: (Message?) -> Unit){
        println("invoke refresh ve jwt "+refreshToken.refreshToken+" "+jwtToken)

        val retrofit =ServiceBuilderLogout.buildLogoutService(LogoutAPIService::class.java)
        retrofit.logoutAuth("Bearer "+jwtToken,refreshToken).enqueue(
            object :Callback<Message>{
                override fun onFailure(call: Call<Message>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    val message=response.body()!!
                    println("İnvoke Durum "+message.message)
                    onResult(message)
                }

            }
        )
    }
}