package com.example.exp_4

import java.io.Serializable

data class Movie(
    val imageResId: Int,
    var name: String,
    var price: Double
) : Serializable


