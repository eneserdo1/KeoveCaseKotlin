package com.eneserdogan.keovecasekotlin.viewmodel

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eneserdogan.keovecasekotlin.LogOutServices.RestApiLogout
import com.eneserdogan.keovecasekotlin.RefreshTokenServices.RestApiRefresh
import com.eneserdogan.keovecasekotlin.View.MainActivity
import com.eneserdogan.keovecasekotlin.model.LoginInfo
import com.eneserdogan.keovecasekotlin.model.Message
import com.eneserdogan.keovecasekotlin.model.RefreshModel
import com.eneserdogan.keovecasekotlin.model.Weather
import com.eneserdogan.keovecasekotlin.weatherServices.WeatherAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel:ViewModel() {

    val infoData=MutableLiveData<LoginInfo>()
    val weathers=MutableLiveData<List<Weather>>()
    val logOutData=MutableLiveData<Message>()

    fun LogOut(refreshToken:String,jwtToken:String){
        val restApi= RestApiLogout()
        val refreshModel= RefreshModel(refreshToken)
        restApi.invokeToken(refreshModel,jwtToken.toString()){message ->
            message?.let {
                logOutData.value=it
            }
        }
    }

    fun getWeatherData(jwtToken: String){
        val api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://testcase1.keove.com/")
            .build()

        val getServis = api.create(WeatherAPIService::class.java)
        val myCall = getServis.getData("Bearer " + jwtToken)
        myCall.enqueue(object : Callback<List<Weather>> {
            override fun onResponse(call: Call<List<Weather>>, response: Response<List<Weather>>) {
                weathers.value=response.body()!!
                println("Weather response girdi")
            }

            override fun onFailure(call: Call<List<Weather>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }
        })
    }

    fun timeToRefresh(refreshToken: String){
        val restApi = RestApiRefresh()
        val token = RefreshModel(refreshToken)
        restApi.getnewToken(token) { newData ->
            newData?.let {
                println("Girdi")
                println("yeni jwt token " + it.jwtToken)
                println("yeni refreshtoken " + it.refreshToken)
                infoData.value=it
            }
        }
    }



}