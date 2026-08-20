package com.example.coffieshopapp.ViewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffieshopapp.data.Model.Coffee
import com.example.coffieshopapp.data.local.AppDatabase
import com.example.coffieshopapp.data.local.FavItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FavViewModel(application: Application) : AndroidViewModel(application) {
    private val favDao = AppDatabase.Companion.getDatabase(application).favDao()

    val allFavItems: Flow<List<FavItem>> = favDao.getAllFavItems()

    fun isFavorite(coffeeId: Int): Flow<Boolean> = favDao.isFavorite(coffeeId)

    fun toggleFavorite(context: Context, coffee: Coffee) {
        viewModelScope.launch {
            val coffeeId = coffee.id ?: return@launch
            val isFav = favDao.isFavorite(coffeeId).first()
            if (isFav) {
                favDao.deleteFavItemById(coffeeId)
                Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
            } else {
                favDao.insertFavItem(
                    FavItem(
                        id = coffeeId,
                        name = coffee.name ?: "",
                        price = coffee.price ?: 0.0,
                        currency = coffee.currency ?: "$",
                        imageUrl = coffee.imageUrl ?: "",
                        description = coffee.description ?: "",
                        rating = coffee.rating ?: 0.0
                    )
                )
                Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun removeFavorite(context: Context, coffeeId: Int) {
        viewModelScope.launch {
            favDao.deleteFavItemById(coffeeId)
            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
        }
    }
}