package com.example.educationalloanwithintents
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val nameEditText: EditText = findViewById(R.id.editTextName)
        val phoneEditText: EditText = findViewById(R.id.editTextPhone)
        val emailEditText: EditText = findViewById(R.id.editTextEmail)
        val checkboxAgree: CheckBox = findViewById(R.id.checkBoxAgree)
        val radioGroupPurpose: RadioGroup = findViewById(R.id.radioGroupPurpose)
        val spinnerCategory: Spinner = findViewById(R.id.spinnerCategory)
        val switchPreference: Switch = findViewById(R.id.switchPreference)
        val buttonSubmit: Button = findViewById(R.id.buttonSubmit)
        buttonSubmit.setOnClickListener {
            val name = nameEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val email = emailEditText.text.toString()
            val isAgree = checkboxAgree.isChecked
            val selectedPurposeId = radioGroupPurpose.checkedRadioButtonId
            val selectedPurpose = findViewById<RadioButton>(selectedPurposeId).text.toString()
            val selectedCategory = spinnerCategory.selectedItem.toString()
            val isPreferenceEnabled = switchPreference.isChecked
            val intent = Intent(this, DisplayActivity::class.java).apply {
                putExtra("NAME", name)
                putExtra("PHONE", phone)
                putExtra("EMAIL", email)
                putExtra("IS_AGREE", isAgree)
                putExtra("PURPOSE", selectedPurpose)
                putExtra("CATEGORY", selectedCategory)
                putExtra("PREFERENCE", isPreferenceEnabled)
            }
            startActivity(intent)
        }
        val imageViewPhone: ImageView = findViewById(R.id.imageViewPhone)
        imageViewPhone.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:")
            }
            startActivity(dialIntent)
        }
    }
}
