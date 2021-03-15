package com.example.tugassoavincentardyanputra2101658344.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.tugassoavincentardyanputra2101658344.api.MealDBService
import com.example.tugassoavincentardyanputra2101658344.db.MealAPIDatabase
import com.example.tugassoavincentardyanputra2101658344.util.Constant
import com.example.tugassoavincentardyanputra2101658344.util.SharePref
import com.example.tugassoavincentardyanputra2101658344.util.livedata.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl(): String = Constant.BASE_URL

    @Singleton
    @Provides
    fun provideSharedPref(
        @ApplicationContext context: Context
    ) = SharePref(context)

    @Singleton
    @Provides
    fun provideInterceptor(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(interceptor: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(interceptor)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideMealDBService(retrofit: Retrofit) =
        retrofit.create(MealDBService::class.java)

    @Singleton
    @Provides
    fun provideMealAPIDatabase(
        @ApplicationContext context: Context
    ): MealAPIDatabase =
        Room.databaseBuilder(
            context,
            MealAPIDatabase::class.java,
            Constant.MEAL_API_DATABASE
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideAllMealItemDao(db: MealAPIDatabase) = db.allMealItemDao()

    @Singleton
    @Provides
    fun provideBestFoodOfTheDayDao(db: MealAPIDatabase) = db.bestFoodOfTheDayDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: MealAPIDatabase) = db.categoryDao()

    @Singleton
    @Provides
    fun provideFavoriteDao(db: MealAPIDatabase) = db.favoriteDao()

    @Singleton
    @Provides
    fun provideFilterTypeDao(db: MealAPIDatabase) = db.filterTypeDao()

    @Singleton
    @Provides
    fun provideMostPopularFoodDao(db: MealAPIDatabase) = db.mostPopularFoodDao()
}