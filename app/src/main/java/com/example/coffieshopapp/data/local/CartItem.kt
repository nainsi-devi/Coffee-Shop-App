package com.example.coffieshopapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val currency: String,
    val imageUrl: String,
    val description: String,
    var quantity: Int
)