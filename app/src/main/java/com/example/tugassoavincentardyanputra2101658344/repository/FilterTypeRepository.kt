package com.example.tugassoavincentardyanputra2101658344.repository

import com.example.tugassoavincentardyanputra2101658344.api.MealDBService
import com.example.tugassoavincentardyanputra2101658344.db.dao.FilterTypeDao
import com.example.tugassoavincentardyanputra2101658344.db.entity.FilterType
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterTypeRepository @Inject constructor(
    private val mealDBService: MealDBService,
    private val filterTypeDao: FilterTypeDao
) {

    fun getFilter(): Flowable<Response<out Any>> =
        Single.merge(
            mealDBService.getCategoryFilter("list"),
            mealDBService.getAreaFilter("list"),
            mealDBService.getIngredientFilter("list")
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

    suspend fun insertFilterType(filterType: List<FilterType>) {
        filterTypeDao.insertFilterType(filterType)
    }

    suspend fun getAllFilterType(): List<FilterType> =
        filterTypeDao.getAllFilterType()
}