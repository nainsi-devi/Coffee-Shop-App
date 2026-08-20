package com.example.coffieshopapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coffieshopapp.data.local.FavItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {
    @Query("SELECT * FROM fav_items")
    fun getAllFavItems(): Flow<List<FavItem>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertFavItem(favItem: FavItem)

    @Delete
    suspend fun deleteFavItem(favItem: FavItem)

    @Query("DELETE FROM fav_items WHERE id = :itemId")
    suspend fun deleteFavItemById(itemId: Int)

    @Query("SELECT EXISTS(SELECT * FROM fav_items WHERE id = :itemId)")
    fun isFavorite(itemId: Int): Flow<Boolean>
}