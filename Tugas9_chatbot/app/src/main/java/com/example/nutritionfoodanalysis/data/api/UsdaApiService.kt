package com.example.nutritionfoodanalysis.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface UsdaApiService {
    @GET("fdc/v1/foods/search")
    suspend fun searchFood(
        @Query("query") query: String,
        @Query("api_key") apiKey: String,
        @Query("pageSize") pageSize: Int = 1
    ): FoodSearchResponse
}

data class FoodSearchResponse(
    val foods: List<FoodItem>
)

data class FoodItem(
    val fdcId: Int,
    val description: String,
    val foodNutrients: List<FoodNutrient>
)

data class FoodNutrient(
    val nutrientId: Int,
    val nutrientName: String,
    val unitName: String,
    val value: Double
)
