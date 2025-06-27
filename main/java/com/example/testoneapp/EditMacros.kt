package com.example.testoneapp
//добавила поле для ввода команды и кнопку обновления
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import kotlin.collections.listOf

@Suppress("DEPRECATION")
class EditMacros : Activity() {

    lateinit var editName: EditText
    var selectedButtonIndex = 0
    var contents = Array(25){""}

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_macros)

        editName = findViewById(R.id.edit_name)
        val saveButton: Button = findViewById(R.id.btn_save)
        val back: ImageButton = findViewById(R.id.btn_back)

        val editButtons = listOf(R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6
            , R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13
            , R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19
            , R.id.btn20, R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24)

        editButtons.forEachIndexed { index, buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                selectedButtonIndex = index + 1
                editName.setText(contents[selectedButtonIndex])
            }
        }

        saveButton.setOnClickListener {
            if (selectedButtonIndex > 0) {
                contents[selectedButtonIndex] = editName.text.toString()
                findViewById<Button>(editButtons[selectedButtonIndex - 1]).text =
                    editName.text.toString()
            }

           /* val intent = Intent(this@EditMacros, Terminal::class.java)
            val name = editName
            intent.putExtra("name", name.toString())*/
            }


        back.setOnClickListener {
            val intent = Intent(this@EditMacros, Terminal::class.java)
            startActivity(intent)
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

