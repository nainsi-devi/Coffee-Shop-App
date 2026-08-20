package com.example.coffieshopapp.Repository

import com.example.coffieshopapp.data.local.CartDao
import com.example.coffieshopapp.data.local.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {
    val allItems: Flow<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun addItem(cartItem: CartItem) {
        val existingItem = cartDao.getCartItemById(cartItem.id)
        if (existingItem != null) {
            existingItem.quantity += cartItem.quantity
            cartDao.updateCartItem(existingItem)
        } else {
            cartDao.insertCartItem(cartItem)
        }
    }

    suspend fun updateQuantity(itemId: Int, quantity: Int) {
        val item = cartDao.getCartItemById(itemId)
        if (item != null) {
            if (quantity > 0) {
                item.quantity = quantity
                cartDao.updateCartItem(item)
            } else {
                cartDao.deleteCartItem(item)
            }
        }
    }

    suspend fun deleteItem(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}