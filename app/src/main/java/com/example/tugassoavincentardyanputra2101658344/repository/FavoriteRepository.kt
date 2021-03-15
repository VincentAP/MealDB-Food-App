package com.example.tugassoavincentardyanputra2101658344.repository

import androidx.lifecycle.LiveData
import com.example.tugassoavincentardyanputra2101658344.db.dao.FavoriteDao
import com.example.tugassoavincentardyanputra2101658344.db.entity.Favorite
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    private val favoriteDao: FavoriteDao
) {

    val allFavoriteData: LiveData<List<Favorite>> = favoriteDao.getAllFavorite()

    suspend fun insertFavorite(mealItem: Favorite) {
        favoriteDao.insertFavorite(mealItem)
    }

    suspend fun deleteFavorite(mealItem: Favorite) {
        favoriteDao.deleteFavorite(mealItem)
    }

    suspend fun deleteFavoriteById(idMeal: String) {
        favoriteDao.deleteFavoriteById(idMeal)
    }
}