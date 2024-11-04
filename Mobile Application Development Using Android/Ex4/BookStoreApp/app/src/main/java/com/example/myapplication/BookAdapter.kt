package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class BookAdapter(private val books: List<Book>, private val onClick: (Book) -> Unit) : BaseAdapter() {

    override fun getCount(): Int = books.size

    override fun getItem(position: Int): Book = books[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.book_item, parent, false)

        val book = getItem(position)

        val titleTextView: TextView = view.findViewById(R.id.bookTitle)
        val authorTextView: TextView = view.findViewById(R.id.bookAuthor)
        val priceTextView: TextView = view.findViewById(R.id.bookPrice)
        val coverImageView: ImageView = view.findViewById(R.id.bookCover)

        titleTextView.text = book.title
        authorTextView.text = book.author
        priceTextView.text = "$${String.format("%.2f", book.price)}"
        coverImageView.setImageResource(book.cover)

        view.setOnClickListener { onClick(book) }

        return view
    }
}
