package com.example.tugassoavincentardyanputra2101658344.api

import androidx.lifecycle.LiveData
import com.example.tugassoavincentardyanputra2101658344.api.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDBService {

    @GET("api/json/v1/1/search.php")
    fun getMostPopularFood(
        @Query("f") f: String
    ): LiveData<ApiResponse<MealBaseResponse>>

    @GET("api/json/v1/1/random.php")
    fun getBestFoodOfTheDay(): LiveData<ApiResponse<MealBaseResponse>>

    @GET("api/json/v1/1/random.php")
    fun getRandomFood(): Single<Response<MealBaseResponse>>

    @GET("api/json/v1/1/categories.php")
    fun getCategories(): LiveData<ApiResponse<MealCategoryResponse>>

    @GET("api/json/v1/1/filter.php")
    fun getMealByCategory(
        @Query("c") c: String
    ): Single<Response<MealByFilterResponse>>

    @GET("api/json/v1/1/filter.php")
    fun getMealByArea(
        @Query("a") a: String
    ): Single<Response<MealByFilterResponse>>

    @GET("api/json/v1/1/filter.php")
    fun getMealByIngredient(
        @Query("i") i: String
    ): Single<Response<MealByFilterResponse>>

    @GET("api/json/v1/1/list.php")
    fun getCategoryFilter(
        @Query("c") c: String
    ): Single<Response<MealCategoryFilterResponse>>

    @GET("api/json/v1/1/list.php")
    fun getAreaFilter(
        @Query("a") a: String
    ): Single<Response<MealAreaFilterResponse>>

    @GET("api/json/v1/1/list.php")
    fun getIngredientFilter(
        @Query("i") i: String
    ): Single<Response<MealIngredientsFilterResponse>>

    @GET("api/json/v1/1/search.php")
    fun getMealListBySearch(
        @Query("s") s: String
    ): Single<Response<MealBaseResponse>>

    @GET("api/json/v1/1/lookup.php")
    fun getMealById(
        @Query("i") i: String
    ): Single<Response<MealBaseResponse>>
}