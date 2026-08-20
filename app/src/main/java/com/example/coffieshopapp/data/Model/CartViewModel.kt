package com.example.coffieshopapp.data.Model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffieshopapp.Repository.CartRepository
import com.example.coffieshopapp.data.local.AppDatabase
import com.example.coffieshopapp.data.local.CartItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CartRepository

    init {
        val cartDao = AppDatabase.Companion.getDatabase(application).cartDao()
        repository = CartRepository(cartDao)
    }

    val cartItems: StateFlow<List<CartItem>> = repository.allItems
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())

    fun addToCart(coffee: Coffee, quantity: Int) {
        viewModelScope.launch {
            val cartItem = CartItem(
                id = coffee.id ?: 0,
                name = coffee.name ?: "Unknown",
                price = coffee.price ?: 0.0,
                currency = coffee.currency ?: "$",
                imageUrl = coffee.imageUrl ?: "",
                description = coffee.description ?: "with milk",
                quantity = quantity
            )
            repository.addItem(cartItem)
        }
    }

    fun updateQuantity(itemId: Int, newQuantity: Int) {
        viewModelScope.launch {
            repository.updateQuantity(itemId, newQuantity)
        }
    }

    fun removeItem(cartItem: CartItem) {
        viewModelScope.launch {
            repository.deleteItem(cartItem)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}