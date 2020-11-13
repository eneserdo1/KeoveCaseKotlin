package com.eneserdogan.keovecasekotlin.adapter

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eneserdogan.keovecasekotlin.R
import com.eneserdogan.keovecasekotlin.model.Weather

class WeatherAdapter(private val weatherList: ArrayList<Weather>) :
    RecyclerView.Adapter<WeatherAdapter.MyHolder>() {
    class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val summaryTxt: TextView = view.findViewById(R.id.summaryTxt)
        val celsiusTxt: TextView = view.findViewById(R.id.celsiusTxt)
        val fahrenitTxt: TextView = view.findViewById(R.id.fahrenitTxt)
        val dateTxt: TextView = view.findViewById(R.id.dateTxt)

        fun bindItems(item: Weather) {
            summaryTxt.setText(item.summary)
            celsiusTxt.setText(item.temperatureC.toString() + "°C ")
            fahrenitTxt.setText(item.temperatureF.toString() + "°F")
            dateTxt.setText(item.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bindItems(weatherList.get(position))
    }

    fun updateList(newList: List<Weather>) {
        weatherList.clear()
        weatherList.addAll(newList)
        notifyDataSetChanged()
    }
}