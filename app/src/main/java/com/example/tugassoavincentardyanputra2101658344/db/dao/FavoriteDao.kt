package com.example.tugassoavincentardyanputra2101658344.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tugassoavincentardyanputra2101658344.db.entity.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(mealItem: Favorite)

    @Query("SELECT * FROM favorite")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("DELETE FROM favorite WHERE idMeal = :idMeal")
    suspend fun deleteFavoriteById(idMeal: String)

    @Delete
    suspend fun deleteFavorite(mealItem: Favorite)
}