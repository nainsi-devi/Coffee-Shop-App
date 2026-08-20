package com.example.coffieshopapp.data.Model

import com.google.gson.annotations.SerializedName

data class CoffeeResponse(
    val success: Boolean? = false,
    val message: String? = "",
    val data: List<Coffee>? = emptyList()
)

data class Coffee(
    val id: Int? = 0,
    val name: String? = "",
    val price: Double? = 0.0,
    val currency: String? = "$",
    val rating: Double? = 0.0,
    val category: String? = "",
    val description: String? = "with milk",
    @SerializedName("image")
    val imageUrl: String? = ""
)

data class Offer(
    val id: Int,
    val title: String,
    val price: String,
    val imageRes: Int
)

