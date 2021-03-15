package com.example.tugassoavincentardyanputra2101658344.repository

import androidx.lifecycle.LiveData
import com.example.tugassoavincentardyanputra2101658344.api.ApiResponse
import com.example.tugassoavincentardyanputra2101658344.api.MealDBService
import com.example.tugassoavincentardyanputra2101658344.api.model.MealCategoryResponse
import com.example.tugassoavincentardyanputra2101658344.db.dao.CategoryDao
import com.example.tugassoavincentardyanputra2101658344.db.entity.Category
import com.example.tugassoavincentardyanputra2101658344.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val categoryDao: CategoryDao,
    private val mealDBService: MealDBService
) {
    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadHomepageCategories(): LiveData<Resource<List<Category>>> {
        return object : NetworkBoundResource<List<Category>, MealCategoryResponse>(appExecutors) {
            override fun saveCallResult(item: MealCategoryResponse) {
                item.categories?.toCategory()?.let { categoryDao.insertCategory(it) }
            }

            override fun shouldFetch(data: List<Category>?): Boolean =
                data == null || data.isEmpty() || repoListRateLimit.shouldFetch(Constant.TIMESTAMP_KEY)

            override fun loadFromDb(): LiveData<List<Category>> =
                categoryDao.getAllCategory()

            override fun createCall(): LiveData<ApiResponse<MealCategoryResponse>> =
                mealDBService.getCategories()

            override fun onFetchFailed() {
                repoListRateLimit.reset(Constant.TIMESTAMP_KEY)
            }
        }.asLiveData()
    }
}