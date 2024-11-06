package com.example.ex_5

import ImageAdapter
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var addButton: Button
    private lateinit var listView: ListView
    private val imageUris = mutableListOf<Uri>() // List to hold image URIs
    private lateinit var adapter: ImageAdapter
    private var imagePositionToUpdate: Int? = null

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
        private const val REQUEST_PERMISSION_READ = 100
        private const val REQUEST_IMAGE_UPDATE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = findViewById(R.id.addButton)
        listView = findViewById(R.id.listView)

        // Initialize the custom adapter
        adapter = ImageAdapter(this, imageUris)
        listView.adapter = adapter

        addButton.setOnClickListener {
            checkPermissions() // Call permission check here
        }

        // Set item click listener to show the dropdown menu
        listView.setOnItemClickListener { parent, view, position, id ->
            showPopupMenu(view, position)
        }
    }

    private fun showPopupMenu(view: View, position: Int) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete -> {
                    adapter.removeAt(position) // Remove item from adapter
                    Toast.makeText(this, "Image deleted", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_READ)
        } else {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                when (requestCode) {
                    REQUEST_IMAGE_PICK -> {
                        addImageToGallery(uri)
                        imageUris.add(uri) // Add URI to the list
                        adapter.notifyDataSetChanged() // Notify adapter to refresh ListView
                    }
                    REQUEST_IMAGE_UPDATE -> {
                        imagePositionToUpdate?.let { position ->
                            imageUris[position] = uri // Update URI in list
                            adapter.notifyDataSetChanged() // Refresh ListView
                            imagePositionToUpdate = null
                            Toast.makeText(this, "Image updated!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun addImageToGallery(imageUri: Uri) {
        val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "My Image ${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val newImageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        newImageUri?.let { uri ->
            contentResolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                Toast.makeText(this, "Image added!", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(this, "Failed to add image", Toast.LENGTH_SHORT).show()
        }
    }

    fun startImageUpdate(position: Int) {
        imagePositionToUpdate = position
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_UPDATE)
    }
}
