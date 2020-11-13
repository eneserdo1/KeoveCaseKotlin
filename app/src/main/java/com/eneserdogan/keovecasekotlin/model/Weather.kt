package com.eneserdogan.keovecasekotlin.model

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("date")
    val date:String?,
    @SerializedName("temperatureC")
    val temperatureC:Int?,
    @SerializedName("temperatureF")
    val temperatureF:Int?,
    @SerializedName("summary")
    val summary:String?
)