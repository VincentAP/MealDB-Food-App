package com.example.tugassoavincentardyanputra2101658344.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tugassoavincentardyanputra2101658344.db.entity.BestFoodOfTheDay

@Dao
interface BestFoodOfTheDayDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBestFoodOfTheDay(mealItem: BestFoodOfTheDay)

    @Query("SELECT * FROM food_of_the_day")
    fun getAllData(): LiveData<BestFoodOfTheDay>

    @Query("DELETE FROM food_of_the_day")
    fun deleteAllBestFoodOfTheDay()

    @Query("UPDATE food_of_the_day SET isFavorite = :isFavorite WHERE idMeal = :id")
    suspend fun updateFavoriteField(isFavorite: Boolean, id: String)

    @Query("SELECT * FROM food_of_the_day WHERE idMeal = :idMeal")
    suspend fun getFoodOfTheDayById(idMeal: String): BestFoodOfTheDay

    @Update
    fun updateBestFoodOfTheDay(mealItem: BestFoodOfTheDay)

    @Delete
    fun deleteBestFoodOfTheDay(mealItem: BestFoodOfTheDay)
}