package com.example.testoneapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlin.collections.listOf

@Suppress("DEPRECATION")
class EditMacros : Activity() {

    lateinit var editContent: EditText
    var selectedButtonIndex = 0
    var contents = Array(25){""}

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_macros)

        editContent = findViewById(R.id.edit_content)
        val saveButton: Button = findViewById(R.id.btn_save)

        val editButtons = listOf(R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6
            , R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13
            , R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19
            , R.id.btn20, R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24)

        editButtons.forEachIndexed { index, buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                selectedButtonIndex = index + 1
                editContent.setText(contents[selectedButtonIndex])
            }
        }

        saveButton.setOnClickListener {
            if (selectedButtonIndex > 0 ){
                contents[selectedButtonIndex] = editContent.text.toString()
                findViewById<Button>(editButtons[selectedButtonIndex - 1]).text = editContent.text.toString()
            }
        }

    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("content", contents)
        setResult(Activity.RESULT_OK, resultIntent)
        super.onBackPressed()
    }

    private fun setContentView() {
        TODO("Not yet implemented")
    }


}

