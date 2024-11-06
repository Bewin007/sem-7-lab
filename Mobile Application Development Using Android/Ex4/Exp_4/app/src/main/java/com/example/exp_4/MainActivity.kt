package com.example.exp_4

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import android.widget.PopupMenu

class MainActivity : AppCompatActivity() {

    private lateinit var movieListView: ListView
    private val movieList = mutableListOf(
        Movie(R.drawable.cover1, "Avatar", 100.00),
        Movie(R.drawable.cover2, "Avengers", 200.00),
        Movie(R.drawable.cover3, "Titanic", 500.00),
        Movie(R.drawable.cover4, "Star Wars", 100.00),
        Movie(R.drawable.cover5, "The Lion King", 300.00)
    )
    private val watchlist = mutableMapOf<Movie, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieListView = findViewById(R.id.movie_list_view)

        val adapter = object : ArrayAdapter<Movie>(this, R.layout.list_item_movie, movieList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false)
                val movie = getItem(position)!!

                view.findViewById<ImageView>(R.id.movie_image).setImageResource(movie.imageResId)
                view.findViewById<TextView>(R.id.movie_name).text = movie.name
                view.findViewById<TextView>(R.id.movie_price).text = "Price: $${movie.price}"

                // Set up the popup menu on item click
                view.setOnClickListener {
                    showPopupMenu(view, movie, position)
                }

                return view
            }
        }
        movieListView.adapter = adapter


        findViewById<Button>(R.id.view_watchlist_button).setOnClickListener {
            val intent = Intent(this, WatchlistActivity::class.java)
            intent.putExtra("watchlist", HashMap(watchlist))
            startActivity(intent)
        }

    }

    // Method to show the popup menu
    private fun showPopupMenu(view: View, movie: Movie, position: Int) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.movie_popup_menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    showEditDialog(movie)
                    true
                }
                R.id.delete -> {
                    movieList.remove(movie)
                    (movieListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                    Toast.makeText(this, "${movie.name} deleted", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.insert -> {
                    showInsertDialog(position)
                    true
                }
                R.id.add_to_watchlist -> {
                    val count = watchlist.getOrDefault(movie, 0) + 1
                    watchlist[movie] = count
                    Toast.makeText(this, "${movie.name} added to watchlist", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    // Method to show the Edit dialog
    private fun showEditDialog(movie: Movie) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_movie, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Movie")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newName = dialogView.findViewById<EditText>(R.id.edit_movie_name).text.toString()
                val newPrice = dialogView.findViewById<EditText>(R.id.edit_movie_price).text.toString().toDouble()
                movie.name = newName
                movie.price = newPrice
                (movieListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                Toast.makeText(this, "${movie.name} updated", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }


    // Method to show the Insert dialog
    private fun showInsertDialog(position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_insert_movie, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Insert Movie")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = dialogView.findViewById<EditText>(R.id.insert_movie_name).text.toString()
                val price = dialogView.findViewById<EditText>(R.id.insert_movie_price).text.toString().toDouble()
                val newMovie = Movie(R.drawable.default_image, name, price)
                movieList.add(position, newMovie)
                (movieListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                Toast.makeText(this, "$name added", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }
}
