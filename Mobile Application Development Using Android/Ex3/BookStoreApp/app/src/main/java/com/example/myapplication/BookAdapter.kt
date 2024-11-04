package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
class BookAdapter(
    private val bookList: List<Book>,
    private val addToCart: (Book) -> Unit
) : BaseAdapter() {
    override fun getCount(): Int = bookList.size
    override fun getItem(position: Int): Any = bookList[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.book_item, parent, false)
        val book = bookList[position]
        view.findViewById<TextView>(R.id.bookTitle).text = book.title
        view.findViewById<TextView>(R.id.bookAuthor).text = book.author
        view.findViewById<TextView>(R.id.bookPrice).text = "$${book.price}"
        view.findViewById<ImageView>(R.id.bookCover).setImageResource(book.cover)
        view.findViewById<Button>(R.id.addToCartButton).setOnClickListener {
            addToCart(book)
        }
        return view
    }
}