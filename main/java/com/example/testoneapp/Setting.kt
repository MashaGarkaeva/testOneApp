package com.example.testoneapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.widget.ImageButton
import android.widget.TextView
import java.util.Calendar

class Setting : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_screen)

        val back: ImageButton =  findViewById(R.id.btn_back)

        back.setOnClickListener {
            val intent = Intent(this@Setting, Modul::class.java)
            startActivity(intent)
        }

        val date: TextView = findViewById(R.id.date)
        val time: TextView = findViewById(R.id.time_two)
        val timeStart: TextView = findViewById(R.id.time_start)
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd.mm.yyyy")
        val simpleTimeFormat = SimpleDateFormat("hh:mm:ss")
        val dateText = simpleDateFormat.format(calendar.time)
        val timeText = simpleTimeFormat.format(calendar.time)
        val timeStartText = simpleTimeFormat.format(calendar.time)
        date.text = dateText
        time.text = timeText
        timeStart.text = timeStartText

    }
    private fun setContentView() {
        TODO("Not yet implemented")
    }

}
