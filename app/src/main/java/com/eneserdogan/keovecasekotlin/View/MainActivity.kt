package com.eneserdogan.keovecasekotlin.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eneserdogan.keovecasekotlin.R
import com.eneserdogan.keovecasekotlin.model.User
import com.eneserdogan.keovecasekotlin.AuthorizationServices.RestApi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener {

            /* Edittextlerin doluluk kontrolü yapılıyor*/
            if(userNameEt.text.toString() =="" || passwordEt.text.toString() ==""){
                    Toast.makeText(applicationContext,"Gerekli Alanları Doldurunuz!",Toast.LENGTH_LONG).show()
            }else{
                signin()
            }


        }
    }
    fun signin() {
        /*RestApi sınıfı üzerinden post işlemi yapılıyor*/
        val apiService = RestApi()
        val userInfo = User(username = userNameEt.text.toString(),password = passwordEt.text.toString().toInt())

        apiService.getInfo(userInfo){loginInfo ->
            loginInfo?.let {
                println("loginn "+loginInfo.refreshToken)
                /*refreshToken ve jwtToken Diğer Intent'e gönderiliyor*/
                val intent=Intent(applicationContext,WeatherActivity::class.java)
                intent.putExtra("jwtToken",loginInfo.jwtToken)
                intent.putExtra("refreshToken",loginInfo.refreshToken)
                startActivity(intent)
                Toast.makeText(applicationContext,"Giriş Başarılı",Toast.LENGTH_LONG).show()
            }

        }
    }
}