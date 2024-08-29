package com.example.educationalloanwithintents

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        val name = intent.getStringExtra("NAME")
        val phone = intent.getStringExtra("PHONE")
        val email = intent.getStringExtra("EMAIL")
        val isAgree = intent.getBooleanExtra("IS_AGREE", false)
        val purpose = intent.getStringExtra("PURPOSE")
        val category = intent.getStringExtra("CATEGORY")
        val preference = intent.getBooleanExtra("PREFERENCE", false)
        val textViewData: TextView = findViewById(R.id.textViewData)
        textViewData.text = """
            Name: $name
            Phone: $phone
            Email: $email
            Agree: $isAgree
            Purpose: $purpose
            Category: $category
            Preference: $preference
        """.trimIndent()
    }
}