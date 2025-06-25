package com.example.testoneapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import android.widget.ImageButton

class Terminal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.terminal_screen)

        val back: ImageButton =  findViewById(R.id.btn_back)

        back.setOnClickListener {
            val intent = Intent(this@Terminal, MainActivity::class.java)
            startActivity(intent)
        }

    }
    private fun setContentView() {
        TODO("Not yet implemented")
    }

}
