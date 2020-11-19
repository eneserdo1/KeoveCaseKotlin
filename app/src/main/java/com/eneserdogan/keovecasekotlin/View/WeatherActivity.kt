package com.eneserdogan.keovecasekotlin.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.eneserdogan.keovecasekotlin.LogOutServices.RestApiLogout
import com.eneserdogan.keovecasekotlin.R
import com.eneserdogan.keovecasekotlin.RefreshTokenServices.RestApiRefresh
import com.eneserdogan.keovecasekotlin.adapter.WeatherAdapter
import com.eneserdogan.keovecasekotlin.model.RefreshModel
import com.eneserdogan.keovecasekotlin.model.Weather
import com.eneserdogan.keovecasekotlin.viewmodel.WeatherViewModel
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
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        /*MainAcitivity'den gelen değerler alınıyor*/
        val jwtToken = intent.getStringExtra("jwtToken")
        val refreshToken = intent.getStringExtra("refreshToken")

        /*Adapter set ediliyor*/
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = recyclerAdapter

        viewModel.getWeatherData(jwtToken.toString())

        /*Timer ile 5 dk geri sayım başlıyor*/
        val timer = object : CountDownTimer(300000, 1000) {
            override fun onFinish() {
                viewModel.timeToRefresh(refreshToken.toString())
            }
            override fun onTick(p0: Long) {
                println("Geri sayım başladı")
            }
        }
        timer.start()
        //Veriler Observe Ediliyor
        observeData()

        /*Log out durumu dinlenerek tıklanıldığında RestApiLogout üzerinden işlemler başlatılıyor */
        btnLogOut.setOnClickListener {
            viewModel.LogOut(refreshToken.toString(), jwtToken.toString())
            val intent=Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeData() {
        viewModel.weathers.observe(this, Observer { data ->
            data?.let {
                recyclerAdapter.updateList(data)
            }
        })
        viewModel.infoData.observe(this, Observer { data ->
            data?.let {
                Toast.makeText(applicationContext, "İnfo Observe Edildi", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.logOutData.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(applicationContext, "Çıkış Yapıldı ${it.message}", Toast.LENGTH_LONG).show()
                println("viewmodel logout")
            }
        })
    }


}

