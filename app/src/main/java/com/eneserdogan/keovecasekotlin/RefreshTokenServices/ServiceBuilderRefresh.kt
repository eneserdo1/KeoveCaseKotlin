package com.eneserdogan.keovecasekotlin.RefreshTokenServices

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilderRefresh {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://testcase1.keove.com/") // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildRefreshService(service: Class<T>): T{
        return retrofit.create(service)
    }
}