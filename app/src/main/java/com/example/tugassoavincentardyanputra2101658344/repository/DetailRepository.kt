package com.example.tugassoavincentardyanputra2101658344.repository

import com.example.tugassoavincentardyanputra2101658344.api.MealDBService
import com.example.tugassoavincentardyanputra2101658344.api.model.MealBaseResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepository @Inject constructor(
    private val mealDBService: MealDBService
) {

    fun getRandomFood(): Single<Response<MealBaseResponse>> =
        mealDBService.getRandomFood()

    fun getMealById(idMeal: String) =
        mealDBService.getMealById(idMeal)
}