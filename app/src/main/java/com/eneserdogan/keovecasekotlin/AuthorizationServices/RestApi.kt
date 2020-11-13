package com.eneserdogan.keovecasekotlin.AuthorizationServices

import com.eneserdogan.keovecasekotlin.model.LoginInfo
import com.eneserdogan.keovecasekotlin.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RestApi {
    fun getInfo(user:User, onResult: (LoginInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(APIService::class.java)
        retrofit.postData(user).enqueue(
            object : Callback<LoginInfo>{
                override fun onFailure(call: Call<LoginInfo>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<LoginInfo>, response: Response<LoginInfo>) {
                    val loginData=response.body()
                    onResult(loginData)
                }

            }
        )
    }


}