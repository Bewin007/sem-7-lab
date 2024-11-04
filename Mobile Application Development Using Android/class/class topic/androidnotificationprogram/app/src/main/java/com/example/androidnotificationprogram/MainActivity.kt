import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidnotificationprogram.R

class MainActivity : AppCompatActivity() {

    private lateinit var productSpinner: Spinner
    private lateinit var costEditText: EditText
    private lateinit var countEditText: EditText
    private lateinit var purchaseButton: Button

    // Example product-price mapping
    private val products = arrayOf("Product 1", "Product 2", "Product 3")
    private val prices = arrayOf(100, 200, 300)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productSpinner = findViewById(R.id.product_spinner)
        costEditText = findViewById(R.id.cost)
        countEditText = findViewById(R.id.count)
        purchaseButton = findViewById(R.id.purchase)

        // Set up spinner with product names
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, products)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        productSpinner.adapter = adapter

        // Set price when a product is selected
        productSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                costEditText.setText(prices[position].toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                costEditText.setText("")
            }
        }

        // Purchase button click event
        purchaseButton.setOnClickListener {
            val selectedProduct = productSpinner.selectedItem.toString()
            val count = countEditText.text.toString()

            if (count.isEmpty()) {
                Toast.makeText(this, "Please enter a count", Toast.LENGTH_SHORT).show()
            } else {
                sendPurchaseNotification(selectedProduct, count)
            }
        }
    }

    // Simulate sending a notification
    private fun sendPurchaseNotification(product: String, count: String) {
        val message = "Purchased $count of $product"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
