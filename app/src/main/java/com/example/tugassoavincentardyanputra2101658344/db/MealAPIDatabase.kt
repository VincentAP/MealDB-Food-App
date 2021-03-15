package com.example.tugassoavincentardyanputra2101658344.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tugassoavincentardyanputra2101658344.db.dao.*
import com.example.tugassoavincentardyanputra2101658344.db.entity.*

@Database(
    entities = [
        AllMealItem::class,
        BestFoodOfTheDay::class,
        Category::class,
        AllMealByFilter::class,
        Favorite::class,
        FilterType::class,
        MostPopularFood::class
    ],
    version = 2
)
abstract class MealAPIDatabase : RoomDatabase() {

    abstract fun allMealItemDao(): AllMealItemDao

    abstract fun bestFoodOfTheDayDao(): BestFoodOfTheDayDao

    abstract fun categoryDao(): CategoryDao

    abstract fun favoriteDao(): FavoriteDao

    abstract fun filterTypeDao(): FilterTypeDao

    abstract fun mostPopularFoodDao(): MostPopularFoodDao
}