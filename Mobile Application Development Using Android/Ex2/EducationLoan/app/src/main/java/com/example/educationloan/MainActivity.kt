package com.example.educationloan

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val et1 = findViewById<EditText>(R.id.et1)
        val et2 = findViewById<EditText>(R.id.et2)
        val r1 = findViewById<RadioButton>(R.id.r1)
        val r2 = findViewById<RadioButton>(R.id.r2)
        val et3 = findViewById<EditText>(R.id.et3)
        val et4 = findViewById<EditText>(R.id.et4)
        val et5 = findViewById<EditText>(R.id.et5)
        val et6 = findViewById<EditText>(R.id.et6)
        val et7 = findViewById<EditText>(R.id.et7)
        val et8 = findViewById<EditText>(R.id.et8)
        val et9 = findViewById<EditText>(R.id.et9)
        val et10 = findViewById<EditText>(R.id.et10)
        val b1 = findViewById<Button>(R.id.b1)

        b1.setOnClickListener {
            // Get the text from the EditText fields
            val value1 = et1.text.toString()
            val value2 = et2.text.toString()
            val value3 = et3.text.toString()
            val value4 = et4.text.toString()
            val value5 = et5.text.toString()
            val value6 = et6.text.toString()
            val value7 = et7.text.toString()
            val value8 = et8.text.toString()
            val value9 = et9.text.toString()
            val value10 = et10.text.toString()

            // Determine which radio button is selected
            val selectedRadioButton = when {
                r1.isChecked -> "RadioButton 1 selected"
                r2.isChecked -> "RadioButton 2 selected"
                else -> "No RadioButton selected"
            }

            // Concatenate all the values into a single string


            et1.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    // You can add logic here if needed after the text is changed.
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    // Logic before the text is changed.
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    // Check if the text matches the regex
                    if (s.matches(Regex("^[A-Za-z]*$"))) {
                        // Text contains only alphabetic characters
                        // You can add logic here, like enabling a button or showing a message.
                    } else {
                        Toast.makeText(this, "Name can Only contain text", Toast.LENGTH_LONG).show()
                    }
                }
//                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            })
//            if (value1 == Regex("[A-Za-z]*")){
//
//            }
//            val message = """
//                Value 1: $value1
//                Value 2: $value2
//                $selectedRadioButton
//                Value 3: $value3
//                Value 4: $value4
//                Value 5: $value5
//                Value 6: $value6
//                Value 7: $value7
//                Value 8: $value8
//                Value 9: $value9
//                Value 10: $value10
//            """.trimIndent()
//
//            // Show the message in a Toast
//            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}
