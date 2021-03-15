package com.example.tugassoavincentardyanputra2101658344.repository

import com.example.tugassoavincentardyanputra2101658344.api.MealDBService
import com.example.tugassoavincentardyanputra2101658344.db.dao.AllMealItemDao
import com.example.tugassoavincentardyanputra2101658344.db.entity.AllMealByFilter
import com.example.tugassoavincentardyanputra2101658344.db.entity.AllMealItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AllMealItemRepository @Inject constructor(
    private val allMealItemDao: AllMealItemDao,
    private val mealDBService: MealDBService
) {

    fun getMealByCategory(category: String) =
        mealDBService.getMealByCategory(category)

    fun getMealByArea(area: String) =
        mealDBService.getMealByArea(area)

    fun getMealByIngredient(ingredient: String) =
        mealDBService.getMealByIngredient(ingredient)

    fun getMealBySearch(str: String) =
        mealDBService.getMealListBySearch(str)

    suspend fun insertAllMealByFilter(mealItem: List<AllMealByFilter>) {
        allMealItemDao.insertAllMealByFilter(mealItem)
    }

    suspend fun getMealByCategoryFromDB(category: String): List<AllMealByFilter> =
        allMealItemDao.getMealByCategory(category)

    suspend fun getMealByAreaFromDB(area: String): List<AllMealByFilter> =
        allMealItemDao.getMealByArea(area)

    suspend fun getMealByIngredientFromDB(ingredient: String): List<AllMealByFilter> =
        allMealItemDao.getMealByIngredient(ingredient)

    suspend fun getMealBySearchFromDB(query: String): List<AllMealByFilter> =
        allMealItemDao.getMealBySearch(query)

    suspend fun insertMeal(mealItem: List<AllMealItem>) {
        allMealItemDao.insertMeal(mealItem)
    }

    suspend fun getAllDataById(idMeal: String): AllMealItem =
        allMealItemDao.getAllDataById(idMeal)
}