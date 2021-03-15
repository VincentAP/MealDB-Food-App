package com.example.tugassoavincentardyanputra2101658344.repository

import androidx.lifecycle.LiveData
import com.example.tugassoavincentardyanputra2101658344.api.ApiResponse
import com.example.tugassoavincentardyanputra2101658344.api.MealDBService
import com.example.tugassoavincentardyanputra2101658344.api.model.MealBaseResponse
import com.example.tugassoavincentardyanputra2101658344.db.dao.MostPopularFoodDao
import com.example.tugassoavincentardyanputra2101658344.db.entity.MostPopularFood
import com.example.tugassoavincentardyanputra2101658344.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MostPopularFoodRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val mostPopularFoodDao: MostPopularFoodDao,
    private val mealDBService: MealDBService
) {
    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadMeals(): LiveData<Resource<List<MostPopularFood>>> {
        return object :
            NetworkBoundResource<List<MostPopularFood>, MealBaseResponse>(appExecutors) {
            override fun saveCallResult(item: MealBaseResponse) {
                item.meals?.toMostPopularFood()
                    ?.let { mostPopularFoodDao.insertMostPopularFood(it) }
            }

            override fun shouldFetch(data: List<MostPopularFood>?): Boolean {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(Constant.TIMESTAMP_KEY)
            }

            override fun loadFromDb(): LiveData<List<MostPopularFood>> =
                mostPopularFoodDao.getAllMostPopularFood()


            override fun createCall(): LiveData<ApiResponse<MealBaseResponse>> =
                mealDBService.getMostPopularFood("e")

            override fun onFetchFailed() {
                repoListRateLimit.reset(Constant.TIMESTAMP_KEY)
            }
        }.asLiveData()
    }

    suspend fun updateFavoriteField(isFavorite: Boolean, id: String) {
        mostPopularFoodDao.updateFavoriteField(isFavorite, id)
    }

    suspend fun getMostPopularFoodById(idMeal: String): MostPopularFood {
        return mostPopularFoodDao.getMostPopularFoodById(idMeal)
    }
}