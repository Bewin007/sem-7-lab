package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var bookListView: ListView
    private val cart = mutableListOf<Book>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bookListView = findViewById(R.id.bookListView)
        val books = listOf(
            Book("Fiction Book 1", "Delwin", 9.99, R.drawable.cover1),
            Book("Non-Fiction Book 2", "Sharoan", 12.99, R.drawable.cover2),
            Book("Academic Book 3", "Rhenius", 15.99, R.drawable.cover3),
            Book("Fiction Book 4", "Jones", 8.99, R.drawable.cover4),
            Book("Non-Fiction Book 5", "Micheal", 10.99, R.drawable.cover5),
            Book("Academic Book 6", "Felix", 20.00, R.drawable.cover6),
            Book("Fiction Book 7", "Bewin", 11.99, R.drawable.cover7),
            Book("Non-Fiction Book 8", "John", 14.99, R.drawable.cover8),
            Book("Academic Book 9", "Author I", 18.50, R.drawable.cover9),
            Book("Fiction Book 10", "Author J", 7.99, R.drawable.cover10)
        )
        val bookAdapter = BookAdapter(books) { book ->
            addToCart(book)
        }
        bookListView.adapter = bookAdapter
    }
    private fun addToCart(book: Book) {
        cart.add(book)
        Toast.makeText(this, "${book.title} added to cart", Toast.LENGTH_SHORT).show()
    }
    fun viewCart(view: View) {
        val intent = Intent(this, CartActivity::class.java)
        intent.putParcelableArrayListExtra("cartItems", ArrayList(cart))
        startActivity(intent)
    }
}