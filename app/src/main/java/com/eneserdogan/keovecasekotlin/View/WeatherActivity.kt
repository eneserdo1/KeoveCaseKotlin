package com.eneserdogan.keovecasekotlin.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.eneserdogan.keovecasekotlin.LogOutServices.RestApiLogout
import com.eneserdogan.keovecasekotlin.R
import com.eneserdogan.keovecasekotlin.RefreshTokenServices.RestApiRefresh
import com.eneserdogan.keovecasekotlin.adapter.WeatherAdapter
import com.eneserdogan.keovecasekotlin.model.RefreshModel
import com.eneserdogan.keovecasekotlin.model.Weather
import com.eneserdogan.keovecasekotlin.weatherServices.WeatherAPIService
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherActivity : AppCompatActivity() {

    private val recyclerAdapter = WeatherAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        /*MainAcitivity'den gelen değerler alınıyor*/
        val jwtToken = intent.getStringExtra("jwtToken")
        val refreshToken = intent.getStringExtra("refreshToken")

        /*Adapter set ediliyor*/
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = recyclerAdapter

        /*Timer ile 5 dk geri sayım başlıyor*/
        val timer = object : CountDownTimer(300000, 1000) {
            override fun onFinish() {
                timeToRefresh(refreshToken.toString())
            }
            override fun onTick(p0: Long) {
                println("Geri sayım başladı")
            }
        }
        timer.start()

        /*Log out durumu dinlenerek tıklanıldığında RestApiLogout üzerinden işlemler başlatılıyor */
        btnLogOut.setOnClickListener {
            val restApi=RestApiLogout()
            val refreshModel=RefreshModel(refreshToken)
            restApi.invokeToken(refreshModel,jwtToken.toString()){message ->
                message?.let {
                    Toast.makeText(applicationContext,"Çıkış Yapıldı",Toast.LENGTH_LONG).show()
                    val intent=Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        /*Hava durumu verileri çekilerek adaptera set ediliyor*/
        lifecycleScope.launch {
            val api = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://testcase1.keove.com/")
                .build()

            val getServis = api.create(WeatherAPIService::class.java)
            val myCall = getServis.getData("Bearer " + jwtToken)
            myCall.enqueue(object : Callback<List<Weather>> {
                override fun onResponse(
                    call: Call<List<Weather>>,
                    response: Response<List<Weather>>
                ) {
                    val weathers = response.body()!!
                    println("Weather response girdi")
                    recyclerAdapter.updateList(weathers)
                }

                override fun onFailure(call: Call<List<Weather>>, t: Throwable) {
                    Log.e("ERROR", t.message.toString())
                    Toast.makeText(applicationContext, "Cevap Gelmedi", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
    }

    /*5 dk dolunca bu fonksiyon çalışarak yeni jwtToken alınır*/
    fun timeToRefresh(refreshToken: String) {
        println("gelen refreshToken" + refreshToken)

        val restApi = RestApiRefresh()
        val token = RefreshModel(refreshToken)
        restApi.getnewToken(token) { newData ->
            newData?.let {
                println("Girdi")
                println("yeni jwt token " + it.jwtToken)
                println("yeni refreshtoken " + it.refreshToken)
            }
        }
    }



}

