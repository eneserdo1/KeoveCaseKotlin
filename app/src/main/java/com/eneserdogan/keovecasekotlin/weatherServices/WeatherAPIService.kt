package com.eneserdogan.keovecasekotlin.weatherServices

import com.eneserdogan.keovecasekotlin.model.LoginInfo
import com.eneserdogan.keovecasekotlin.model.User
import com.eneserdogan.keovecasekotlin.model.Weather
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface WeatherAPIService {
    @GET("weatherforecast")
    fun getData(@Header("Authorization") authHeader:String): Call<List<Weather>>
}