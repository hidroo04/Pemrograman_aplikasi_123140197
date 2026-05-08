package com.example.nutritionfoodanalysis.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_analysis_cache")
data class FoodAnalysisCache(
    @PrimaryKey
    val foodName: String,
    val analysisText: String,
    val timestamp: Long = System.currentTimeMillis()
)
