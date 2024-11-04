package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "bookstore.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_BOOKS = "books"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_AUTHOR = "author"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_COVER = "cover"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_BOOKS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT, $COLUMN_AUTHOR TEXT, $COLUMN_PRICE REAL, $COLUMN_COVER INTEGER)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKS")
        onCreate(db)
    }

    // CRUD Operations
    fun addBook(book: Book) {
        val values = ContentValues().apply {
            put(COLUMN_TITLE, book.title)
            put(COLUMN_AUTHOR, book.author)
            put(COLUMN_PRICE, book.price)
            put(COLUMN_COVER, book.cover)
        }
        writableDatabase.insert(TABLE_BOOKS, null, values)
    }

    fun getAllBooks(): List<Book> {
        val books = mutableListOf<Book>()
        val cursor: Cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE_BOOKS", null)
        if (cursor.moveToFirst()) {
            do {
                val book = Book(
                    title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                    author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)),
                    price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)),
                    cover = cursor.getInt(cursor.getColumnIndex(COLUMN_COVER))
                )
                books.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return books
    }

    fun clearBooks() {
        writableDatabase.execSQL("DELETE FROM $TABLE_BOOKS")
    }
}
