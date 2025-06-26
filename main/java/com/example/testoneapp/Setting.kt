package com.example.testoneapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import java.util.Calendar

class Setting : ComponentActivity() {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")

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
        val editTime: ImageView = findViewById(R.id.btn_edit_time)

        editDate.setOnClickListener{
            /*val inputDate = EditText(this)
            inputDate.hint = "Введите дату"

            AlertDialog.Builder(this)
                .setTitle("Введите дату")
                .setView(inputDate)
                .setPositiveButton("Ок"){_, _ ->
                    val newDate = inputDate.text.toString()
                    if (isValidDate(newDate)){
                        date.text = "$newDate"
                    } else {
                        Toast.makeText(this, "Неверный формат даты", Toast.LENGTH_SHORT).show()
                    }

                }.setNegativeButton("Отмена", null).show()

            save.setOnClickListener {
                val intent = Intent(this@Setting, Modul::class.java)
                startActivity(intent)
            }*/

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

        editTime.setOnClickListener {
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(
                this,
                {_: TimePicker, selectedHour: Int, selectedMinute: Int ->
                    val formatedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    time.text = "$formatedTime"
                }, hour, minute, true
            ).show()
        }
    }

    private fun setContentView() {
        TODO("Not yet implemented")
    }

    fun Setting.isValidDate(date: kotlin.String): kotlin.Boolean {
        return try {
            simpleDateFormat.parse(date)
            true
        } catch (e: Exception){
            false
        }
    }


}

