package com.example.bmicalculator


import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val t1 = findViewById<EditText>(R.id.t1)
        val t2 = findViewById<EditText>(R.id.t2)
        val b1 = findViewById<Button>(R.id.b1)
        val t4 = findViewById<TextView>(R.id.t4)

        b1.setOnClickListener{
            val ht = t1.text.toString()
            val wt = t2.text.toString()

            if(ht.isNotEmpty() && wt.isNotEmpty()) {
                val height = ht.toFloat()
                val weight = wt.toFloat()
                val bmi = weight / (height * height)

                val status = when {
                    bmi < 18 -> "Underweight"
                    bmi in 18.0..24.9 -> "Normal"
                    bmi in 25.0..29.9 -> "Overweight"
                    else -> "Obese"
                }

                val bmiResult = String.format("Your BMI is: %.2f\nStatus: %s", bmi, status)
                t4.text = bmiResult
                var toast = Toast.makeText(this,bmiResult,Toast.LENGTH_LONG)
                toast.show()
            }
            else{
                t4.text = "Please enter valid height and weight"
            }
        }
    }
}
