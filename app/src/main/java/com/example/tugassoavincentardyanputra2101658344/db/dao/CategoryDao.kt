package com.example.tugassoavincentardyanputra2101658344.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tugassoavincentardyanputra2101658344.db.entity.Category

@Dao
interface CategoryDao {

    /**
     * Homepage category
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(categoryItem: List<Category>)

    @Query("SELECT * FROM category")
    fun getAllCategory(): LiveData<List<Category>>

    @Delete
    fun deleteCategory(categoryItem: Category)
}