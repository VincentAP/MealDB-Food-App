package com.example.tugassoavincentardyanputra2101658344.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tugassoavincentardyanputra2101658344.db.entity.MostPopularFood

@Dao
interface MostPopularFoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMostPopularFood(mealItem: List<MostPopularFood>)

    @Query("SELECT * FROM most_popular_food")
    fun getAllMostPopularFood(): LiveData<List<MostPopularFood>>

    @Query("UPDATE most_popular_food SET isFavorite = :isFavorite WHERE idMeal = :id")
    suspend fun updateFavoriteField(isFavorite: Boolean, id: String)

    @Query("SELECT * FROM most_popular_food WHERE idMeal = :idMeal")
    suspend fun getMostPopularFoodById(idMeal: String): MostPopularFood

    @Delete
    fun deleteMostPopularFood(mealItem: MostPopularFood)
}