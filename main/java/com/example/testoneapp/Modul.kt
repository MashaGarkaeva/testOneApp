package com.example.testoneapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.os.BatteryManager
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.Calendar

class Modul : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modulstatus)

        val back: ImageView =  findViewById(R.id.btn_back)
        val setting: ImageView = findViewById(R.id.btn_setting)

        back.setOnClickListener {
            val intent = Intent(this@Modul, MainActivity::class.java)
            startActivity(intent)
        }

        setting.setOnClickListener {
            val intent = Intent(this@Modul, Setting::class.java)
            startActivity(intent)
        }

        val date: TextView = findViewById(R.id.date)
        val time: TextView = findViewById(R.id.time_two)
        val time_general: TextView = findViewById(R.id.time)
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd.mm.yyyy")
        val simpleTimeFormat = SimpleDateFormat("hh:mm:ss")
        val simpleTimeFormatGeneral = SimpleDateFormat("hh:mm")
        val dateText = simpleDateFormat.format(calendar.time)
        val timeText = simpleTimeFormat.format(calendar.time)
        val timeGeneralText = simpleTimeFormatGeneral.format(calendar.time)
        date.text = dateText
        time.text = timeText
        time_general.text = timeGeneralText

        val batterygeneral: TextView = findViewById(R.id.battery)
        val batteryTwo: TextView = findViewById(R.id.battery_two)
        val bm = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager
        val batlevel:Int = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        batterygeneral.text = batlevel.toString()+"%"
        batteryTwo.text = batlevel.toString()+"%"

    }
    private fun setContentView() {
        TODO("Not yet implemented")
    }

}
