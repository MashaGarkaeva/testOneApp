package com.example.testoneapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

@Suppress("DEPRECATION")
class Terminal : ComponentActivity() {
    lateinit var textConteiner: LinearLayout
    val buttonContents = Array(24){
        i -> if(i == 0) ""
        else "Содержимое $i"
    }

    @SuppressLint("MissingInflatedId", "DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.terminal_screen)

        val back: ImageButton =  findViewById(R.id.btn_back)
        val btn_editCommand: Button = findViewById(R.id.btn_edit_command)
        val editText: EditText = findViewById(R.id.edit_command)
        val btn_list_macros: Button = findViewById(R.id.btn_list_macros)
        textConteiner = findViewById(R.id.textConteiner)

        back.setOnClickListener {
            val intent = Intent(this@Terminal, MainActivity::class.java)
            startActivity(intent)
        }

        btn_editCommand.setOnClickListener {
            val text = editText.text.toString()
            addTextToConteiner(text)
            editText.text.clear()
            Toast.makeText(this,"Команда отправлена", Toast.LENGTH_SHORT).show()
        }

        btn_list_macros.setOnClickListener {
            val intent = Intent(this@Terminal, EditMacros::class.java)
            intent.putExtra("contents", buttonContents)
            startActivity(intent)
        }

        val buttons = listOf(R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6
            , R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13
                    , R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19
            , R.id.btn20, R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24)

        buttons.forEachIndexed { index, buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                Toast.makeText(this, buttonContents[index + 1], Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun addTextToConteiner(text: String){
        val textView = TextView(this)
        textView.text = text
        textView.textSize = 18f
        textView.setPadding(0,8,0,8)
        textConteiner.addView(textView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            data?.getStringArrayExtra("contents")?.copyInto(buttonContents)
        }
    }

    }

    private fun setContentView() {
        TODO("Not yet implemented")
    }