package com.example.tugassoavincentardyanputra2101658344.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Category filter section
 * @param filterName, e.g. breakfast
 * @param filterType, e.g. category/area/ingredient
 */
@Entity(tableName = "filter")
data class FilterType(
    @PrimaryKey
    val filterName: String,
    val filterType: String?
)
