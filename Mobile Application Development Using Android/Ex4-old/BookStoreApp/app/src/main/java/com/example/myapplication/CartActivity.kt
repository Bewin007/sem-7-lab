package com.example.myapplication

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {
    private lateinit var cartListView: ListView
    private lateinit var totalPriceTextView: TextView
    private lateinit var cartAdapter: BookAdapter
    private var cartItems: List<Book> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        cartListView = findViewById(R.id.cartListView)
        totalPriceTextView = findViewById(R.id.totalPrice)
        cartItems = intent.getParcelableArrayListExtra("cartItems") ?: emptyList()
        cartAdapter = BookAdapter(cartItems) {}
        cartListView.adapter = cartAdapter
        val totalPrice = cartItems.sumOf { it.price }
        totalPriceTextView.text = "Total: $${String.format("%.2f", totalPrice)}"
    }
}
