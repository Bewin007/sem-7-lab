package com.example.exp_4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class WatchlistAdapter(context: Context, private val watchlist: Map<Movie, Int>)
    : ArrayAdapter<Map.Entry<Movie, Int>>(context, 0, watchlist.entries.toList()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_watchlist, parent, false)

        val entry = getItem(position)
        val movie = entry?.key
        val count = entry?.value ?: 0

        view.findViewById<ImageView>(R.id.watchlist_movie_image).setImageResource(movie?.imageResId ?: R.drawable.default_image)
        view.findViewById<TextView>(R.id.watchlist_movie_name).text = movie?.name ?: "Unknown"
        view.findViewById<TextView>(R.id.watchlist_movie_count).text = "Count: $count"
        view.findViewById<TextView>(R.id.watchlist_movie_total_price).text = "Price: Rs.${movie?.price?.times(count) ?: 0.0}"

        return view
    }
}
