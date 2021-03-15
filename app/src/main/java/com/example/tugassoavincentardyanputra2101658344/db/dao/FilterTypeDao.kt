package com.example.tugassoavincentardyanputra2101658344.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tugassoavincentardyanputra2101658344.db.entity.FilterType

@Dao
interface FilterTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilterType(filterType: List<FilterType>)

    @Query("SELECT * FROM filter")
    suspend fun getAllFilterType(): List<FilterType>
}