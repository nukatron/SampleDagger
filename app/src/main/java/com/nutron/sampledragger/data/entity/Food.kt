package com.nutron.sampledragger.data.entity

import com.google.gson.annotations.SerializedName


data class FoodNutrient(val nutrient: String, val unit: String, val value: String) {

    override fun toString(): String {
        return "$nutrient: $value $unit"
    }
}

data class Food(
        val ndbno: String,
        val name: String,
        val measure: String,
        val nutrients: List<FoodNutrient>? = null
)

data class FoodList(val foods: List<Food>)

data class FoodResponse(@SerializedName("report") val foodList: FoodList)
