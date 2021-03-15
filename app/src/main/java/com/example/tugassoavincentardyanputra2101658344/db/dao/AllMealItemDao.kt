package com.example.tugassoavincentardyanputra2101658344.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tugassoavincentardyanputra2101658344.db.entity.AllMealByFilter
import com.example.tugassoavincentardyanputra2101658344.db.entity.AllMealItem

@Dao
interface AllMealItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeal(mealItem: List<AllMealItem>)

    @Query("SELECT * FROM all_meal_item")
    suspend fun getAllData(): AllMealItem

    @Query("SELECT * FROM all_meal_item WHERE idMeal = :idMeal")
    suspend fun getAllDataById(idMeal: String): AllMealItem

    @Update
    fun updateMeal(mealItem: AllMealItem)

    /**
     * Category page
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMealByFilter(mealItem: List<AllMealByFilter>)

    @Query("SELECT * FROM all_meal_by_category")
    fun getAllMealByFilter(): LiveData<List<AllMealByFilter>>

    @Query("SELECT * FROM all_meal_by_category WHERE strMeal = :query")
    suspend fun getMealBySearch(query: String): List<AllMealByFilter>

    @Query("SELECT * FROM all_meal_by_category WHERE category = :category")
    suspend fun getMealByCategory(category: String): List<AllMealByFilter>

    @Query("SELECT * FROM all_meal_by_category WHERE area = :area")
    suspend fun getMealByArea(area: String): List<AllMealByFilter>

    @Query("SELECT * FROM all_meal_by_category WHERE ingredient = :ingredient")
    suspend fun getMealByIngredient(ingredient: String): List<AllMealByFilter>

    @Delete
    fun deleteAllMealByFilter(mealItem: AllMealByFilter)
}