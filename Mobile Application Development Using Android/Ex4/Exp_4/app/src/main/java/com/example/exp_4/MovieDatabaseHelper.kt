package com.example.exp_4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



class MovieDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "movies.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_MOVIES = "movies"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_MOVIES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_PRICE REAL," +
                "$COLUMN_IMAGE INTEGER)")
        db.execSQL(createTable)

        // Insert default movies
        insertMovie(db, Movie(R.drawable.cover1, "Fiction Book 1", 100.00))
        insertMovie(db, Movie(R.drawable.cover2, "Non-Fiction Book 2", 200.00))
        insertMovie(db, Movie(R.drawable.cover3, "Academic Book 3", 500.00))
        insertMovie(db, Movie(R.drawable.cover4, "Fiction Book 4", 100.00))
        insertMovie(db, Movie(R.drawable.cover5, "Non-Fiction Book 5", 300.00))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIES")
        onCreate(db)
    }

    fun insertMovie(db: SQLiteDatabase, movie: Movie) {
        val values = ContentValues().apply {
            put(COLUMN_NAME, movie.name)
            put(COLUMN_PRICE, movie.price)
            put(COLUMN_IMAGE, movie.imageResId)
        }
        db.insert(TABLE_MOVIES, null, values)
    }

    fun getAllMovies(): MutableList<Movie> {
        val movies = mutableListOf<Movie>()
        val db = readableDatabase
        val cursor = db.query(TABLE_MOVIES, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
                val image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
                movies.add(Movie(image, name, price))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return movies
    }

    fun updateMovie(id: Int, newName: String, newPrice: Double) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, newName)
            put(COLUMN_PRICE, newPrice)
        }
        db.update(TABLE_MOVIES, values, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun deleteMovie(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_MOVIES, "$COLUMN_ID=?", arrayOf(id.toString()))
    }
}
