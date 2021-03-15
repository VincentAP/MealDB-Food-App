package com.example.tugassoavincentardyanputra2101658344.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Homepage category section
 */
@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    val categoryName: String,
    val imageUrl: String?
)