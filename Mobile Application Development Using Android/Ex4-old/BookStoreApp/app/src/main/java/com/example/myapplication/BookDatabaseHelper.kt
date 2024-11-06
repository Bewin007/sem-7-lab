package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BookDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "bookstore.db"
        private const val DATABASE_VERSION = 1
        private const val BOOKS_TABLE = "books"
        private const val CART_TABLE = "cart"

        // Common columns
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_AUTHOR = "author"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_COVER = "cover"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create books table
        val createBooksTable = """
            CREATE TABLE $BOOKS_TABLE (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_AUTHOR TEXT,
                $COLUMN_PRICE REAL,
                $COLUMN_COVER INTEGER
            )
        """.trimIndent()
        db.execSQL(createBooksTable)

        // Create cart table
        val createCartTable = """
            CREATE TABLE $CART_TABLE (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_AUTHOR TEXT,
                $COLUMN_PRICE REAL,
                $COLUMN_COVER INTEGER
            )
        """.trimIndent()
        db.execSQL(createCartTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $BOOKS_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $CART_TABLE")
        onCreate(db)
    }

    // Insert a book into the database
    fun insertBook(book: Book): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, book.title)
            put(COLUMN_AUTHOR, book.author)
            put(COLUMN_PRICE, book.price)
            put(COLUMN_COVER, book.cover)
        }
        return db.insert(BOOKS_TABLE, null, values)
    }

    // Insert a book into the cart
    fun addToCart(book: Book): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, book.title)
            put(COLUMN_AUTHOR, book.author)
            put(COLUMN_PRICE, book.price)
            put(COLUMN_COVER, book.cover)
        }
        return db.insert(CART_TABLE, null, values)
    }

    // Retrieve all books from the cart
    fun getCartItems(): List<Book> {
        val cartList = mutableListOf<Book>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $CART_TABLE", null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR))
                val price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                val cover = cursor.getInt(cursor.getColumnIndex(COLUMN_COVER))
                cartList.add(Book(title, author, price, cover))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cartList
    }

    // Clear the cart (optional)
    fun clearCart() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $CART_TABLE")
    }
}
