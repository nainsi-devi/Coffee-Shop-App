package com.example.coffieshopapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_items")
data class FavItem(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val currency: String,
    val imageUrl: String,
    val description: String,
    val rating: Double
)