package com.example.tugassoavincentardyanputra2101658344.repository

import androidx.lifecycle.LiveData
import com.example.tugassoavincentardyanputra2101658344.api.ApiResponse
import com.example.tugassoavincentardyanputra2101658344.api.MealDBService
import com.example.tugassoavincentardyanputra2101658344.api.model.MealBaseResponse
import com.example.tugassoavincentardyanputra2101658344.db.MealAPIDatabase
import com.example.tugassoavincentardyanputra2101658344.db.dao.BestFoodOfTheDayDao
import com.example.tugassoavincentardyanputra2101658344.db.entity.BestFoodOfTheDay
import com.example.tugassoavincentardyanputra2101658344.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodOfTheDayRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val bestFoodOfTheDayDao: BestFoodOfTheDayDao,
    private val mealAPIDatabase: MealAPIDatabase,
    private val mealDBService: MealDBService
) {
    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadBestFoodOfTheDay(): LiveData<Resource<BestFoodOfTheDay>> {
        return object : NetworkBoundResource<BestFoodOfTheDay, MealBaseResponse>(appExecutors) {
            override fun saveCallResult(item: MealBaseResponse) {
                item.meals?.toBestFoodOfTheDay()?.let {
                    mealAPIDatabase.runInTransaction {
                        bestFoodOfTheDayDao.deleteAllBestFoodOfTheDay()
                        bestFoodOfTheDayDao.insertBestFoodOfTheDay(it[0])
                    }
                }
            }

            override fun shouldFetch(data: BestFoodOfTheDay?): Boolean =
                data == null || repoListRateLimit.shouldFetch(Constant.TIMESTAMP_KEY)

            override fun loadFromDb(): LiveData<BestFoodOfTheDay> =
                bestFoodOfTheDayDao.getAllData()

            override fun createCall(): LiveData<ApiResponse<MealBaseResponse>> =
                mealDBService.getBestFoodOfTheDay()

            override fun onFetchFailed() {
                repoListRateLimit.reset(Constant.TIMESTAMP_KEY)
            }
        }.asLiveData()
    }

    suspend fun updateFavoriteField(isFavorite: Boolean, id: String) {
        bestFoodOfTheDayDao.updateFavoriteField(isFavorite, id)
    }

    suspend fun getFoodOfTheDayById(idMeal: String): BestFoodOfTheDay {
        return bestFoodOfTheDayDao.getFoodOfTheDayById(idMeal)
    }
}