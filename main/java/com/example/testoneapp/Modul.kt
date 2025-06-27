package com.example.testoneapp
//добавила кнопку обновления, при нажатии которой новые дата, время и время запуска меняются
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.os.BatteryManager
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.text.intl.Locale
import java.util.Calendar
import java.util.Date
import kotlin.text.format

class Modul : ComponentActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modulstatus)

        val back: ImageView =  findViewById(R.id.btn_back)
        val setting: ImageView = findViewById(R.id.btn_setting)
        val update: ImageView = findViewById(R.id.btn_update)

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
        val time_start: TextView = findViewById(R.id.time_start)
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        val simpleTimeFormat = SimpleDateFormat("hh:mm:ss")
        val simpleTimeFormatGeneral = SimpleDateFormat("hh:mm")
        val simpleTimeStartFormat = SimpleDateFormat("hh:mm:ss")
        val dateText = simpleDateFormat.format(calendar.time)
        val timeText = simpleTimeFormat.format(calendar.time)
        val timeGeneralText = simpleTimeFormatGeneral.format(calendar.time)
        val timeStart = simpleTimeStartFormat.format(calendar.time)
        date.text = dateText
        time.text = timeText
        time_general.text = timeGeneralText
        time_start.text = timeStart

        val batterygeneral: TextView = findViewById(R.id.battery)
        val batteryTwo: TextView = findViewById(R.id.battery_two)
        val bm = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager
        val batlevel:Int = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        batterygeneral.text = batlevel.toString() + "%"
        batteryTwo.text = batlevel.toString() + "%"

        update.setOnClickListener {
            date.text = intent.getStringExtra("Дата")
            time.text = intent.getStringExtra("Время")
            time_start.text = intent.getStringExtra("Время запуска")
        }
    }

    private fun setContentView() {
        TODO("Not yet implemented")
    }
}


