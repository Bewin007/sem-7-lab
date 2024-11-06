package com.example.exp_4

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WatchlistActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var totalPriceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchlist)

        listView = findViewById(R.id.watchlist_list_view)
        totalPriceTextView = findViewById(R.id.total_price)

        val watchlist = intent.getSerializableExtra("watchlist") as HashMap<Movie, Int>
        val adapter = WatchlistAdapter(this, watchlist)
        listView.adapter = adapter

        // Calculate the total price
        val totalPrice = watchlist.entries.sumOf { it.key.price * it.value }
        totalPriceTextView.text = "Total Price: Rs.${totalPrice}"
    }
}
