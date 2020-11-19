package com.eneserdogan.keovecasekotlin.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eneserdogan.keovecasekotlin.AuthorizationServices.RestApi
import com.eneserdogan.keovecasekotlin.View.WeatherActivity
import com.eneserdogan.keovecasekotlin.model.LoginInfo
import com.eneserdogan.keovecasekotlin.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainViewModel:ViewModel() {

    val data=MutableLiveData<LoginInfo>()


    fun refreshData(userInfo: User){
        signin(userInfo)
    }

    private fun signin(userInfo:User) {
        /*RestApi sınıfı üzerinden post işlemi yapılıyor*/
        val apiService = RestApi()
        apiService.getInfo(userInfo){loginInfo ->
            loginInfo?.let {

                data.value=loginInfo
                /*refreshToken ve jwtToken Diğer Intent'e gönderiliyor*/

            }

        }
    }
}