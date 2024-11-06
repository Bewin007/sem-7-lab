//package com.example.myapplication
//
//import android.os.Parcel
//import android.os.Parcelable
//data class Book(
//    val title: String,
//    val author: String,
//    val price: Double,
//    val cover: Int
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString() ?: "",
//        parcel.readString() ?: "",
//        parcel.readDouble(),
//        parcel.readInt())
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(title)
//        parcel.writeString(author)
//        parcel.writeDouble(price)
//        parcel.writeInt(cover)
//    }
//    override fun describeContents(): Int = 0
//    companion object CREATOR : Parcelable.Creator<Book> {
//        override fun createFromParcel(parcel: Parcel): Book = Book(parcel)
//        override fun newArray(size: Int): Array<Book?> = arrayOfNulls(size)
//    }
//}

package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class Book(
    val title: String,
    val author: String,
    val price: Double,
    val cover: Int
) : Parcelable {

    // Constructor for recreating the Book object from a Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt()
    )

    // Write the Book object data to a Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeDouble(price)
        parcel.writeInt(cover)
    }

    // Required for Parcelable, can usually return 0
    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}

