// MainActivity.kt
package com.example.ex_8

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.ex_8.R

class MainActivity : AppCompatActivity() {

    private lateinit var appointmentDetails: EditText
    private lateinit var recipientEmail: EditText
    private lateinit var recipientPhone: EditText
    private lateinit var sendEmailButton: Button
    private lateinit var sendSmsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appointmentDetails = findViewById(R.id.appointmentDetails)
        recipientEmail = findViewById(R.id.recipientEmail)
        recipientPhone = findViewById(R.id.recipientPhone)
        sendEmailButton = findViewById(R.id.sendEmailButton)
        sendSmsButton = findViewById(R.id.sendSmsButton)

        sendEmailButton.setOnClickListener {
            sendEmail()
        }

        sendSmsButton.setOnClickListener {
            sendSms()
        }

        // Request SMS permission
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.SEND_SMS),
            1)
    }

    private fun sendEmail() {
        val email = recipientEmail.text.toString()
        val subject = "Appointment Reminder"
        val message = appointmentDetails.text.toString()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }

        try {
            startActivity(Intent.createChooser(intent, "Send Email..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "No email client installed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendSms() {
        val phoneNumber = recipientPhone.text.toString()
        val message = appointmentDetails.text.toString()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "SMS permission not granted", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "SMS sent.", Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(this, "Failed to send SMS: ${ex.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
