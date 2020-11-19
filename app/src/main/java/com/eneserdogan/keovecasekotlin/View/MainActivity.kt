package com.eneserdogan.keovecasekotlin.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.eneserdogan.keovecasekotlin.R
import com.eneserdogan.keovecasekotlin.model.User
import com.eneserdogan.keovecasekotlin.AuthorizationServices.RestApi
import com.eneserdogan.keovecasekotlin.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        
        btnLogin.setOnClickListener {
            /* Edittextlerin doluluk kontrolü yapılıyor*/
            if (userNameEt.text.toString() == "" || passwordEt.text.toString() == "") {
                Toast.makeText(
                    applicationContext,
                    "Gerekli Alanları Doldurunuz!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val userInfo = User(
                    username = userNameEt.text.toString(),
                    password = passwordEt.text.toString().toInt()
                )
                viewModel.refreshData(userInfo)
            }
            observeData()
        }
    }


    private fun observeData() {
        viewModel.data.observe(this, Observer { info ->
            info?.let {
                val intent = Intent(applicationContext, WeatherActivity::class.java)
                intent.putExtra("jwtToken", info.jwtToken)
                intent.putExtra("refreshToken", info.refreshToken)
                Toast.makeText(applicationContext,"Giriş Başarılı", Toast.LENGTH_LONG).show()
                startActivity(intent)

            }
        })

    }

}