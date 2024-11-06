package com.example.ex_5

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        val imageView: ImageView = findViewById(R.id.displayImageView)
        val imageUri = intent.getParcelableExtra<Uri>("imageUri") // Get URI from intent

        // Load the image into ImageView
        imageUri?.let {
            Glide.with(this).load(it).into(imageView)
        }
    }
}
