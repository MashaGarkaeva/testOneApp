package com.example.testoneapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Calendar

class MainActivity : Activity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val modul:Button =  findViewById(R.id.btn_model)

        modul.setOnClickListener {
            val intent = Intent(this@MainActivity, Modul::class.java)
            startActivity(intent)
        }

        val terminal:Button =  findViewById(R.id.btn_terminal)

        terminal.setOnClickListener {
            val intent = Intent(this@MainActivity, Terminal::class.java)
            startActivity(intent)
        }

        val time_general: TextView = findViewById(R.id.time)
        val calendar: Calendar = Calendar.getInstance()
        val simpleTimeFormatGeneral = SimpleDateFormat("hh:mm")
        val timeGeneralText = simpleTimeFormatGeneral.format(calendar.time)
        time_general.text = timeGeneralText

        /*      enableEdgeToEdge()
                setContent {
                    TestOneAppTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Greeting(
                                name = "Android",
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }*/
    }

    private fun setContentView() {
        TODO("Not yet implemented")
    }


}


/*@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestOneAppTheme {
        Greeting("Android")
    }
}*/