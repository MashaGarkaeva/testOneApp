package com.example.testoneapp
//изменять данные и передавать их прошлому окну после нажатия кнопки
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class Setting : ComponentActivity() {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
    var currentTime = LocalTime.now()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_screen)

        val back: ImageButton =  findViewById(R.id.btn_back)
        val save: Button = findViewById(R.id.btn_save)

        back.setOnClickListener {
            val intent = Intent(this@Setting, Modul::class.java)
            startActivity(intent)
        }

        var date: TextView = findViewById(R.id.date)
        val timeStart: TextView = findViewById(R.id.time_start)
        val simpleTimeFormat = SimpleDateFormat("hh:mm:ss")
        val calendar: Calendar = Calendar.getInstance()
        var time: TextView = findViewById(R.id.time_two)
        val dateText = simpleDateFormat.format(calendar.time)
        val timeText = simpleTimeFormat.format(calendar.time)
        val timeStartText = simpleTimeFormat.format(calendar.time)
        date.text = dateText
        time.text = timeText
        timeStart.text = timeStartText

        val editDate: ImageView = findViewById(R.id.btn_edit_date)
        val editTimeStart: Button = findViewById(R.id.btn_edit_timeStart)
        val updateTimeStart: Button = findViewById(R.id.btn_update_time)

        editDate.setOnClickListener{
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            var selectedDate = ""

            DatePickerDialog(
                this,
                {_, selectedYear, selectedMonth, selectedDay ->
                    selectedDate = "$selectedDay.${selectedMonth+1}.$selectedYear"
                    date.text = selectedDate
                }, day, month,year).show()
        }


        editTimeStart.setOnClickListener {
            currentTime = currentTime.plusMinutes(15)
            //currentTime.add(Calendar.MINUTE, 15)

            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            timeStart.text = currentTime.format(formatter)

           /* val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val formattedTime = timeFormat.format(currentTime)
            timeStart.text = formattedTime*/
        }

        updateTimeStart.setOnClickListener {
            currentTime = LocalTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            timeStart.text = currentTime.format(formatter)
        }

        save.setOnClickListener {
            val intent = Intent(this@Setting, Modul::class.java)
            intent.putExtra("Дата", date.text)
            intent.putExtra("Время", time.text)
            intent.putExtra("Время запуска", timeStart.text)
            startActivity(intent)
        }
    }


    private fun setContentView() {
        TODO("Not yet implemented")
    }

}

