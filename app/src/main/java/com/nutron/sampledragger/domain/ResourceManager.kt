package com.nutron.sampledragger.domain

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import com.nutron.sampledragger.R

enum class FoodLevel {
    UNKNOWN, RED, GREEN, YELLOW
}

class ResourceManager {

    @ColorRes fun getFoodColor(level: FoodLevel): Int = when(level) {
        FoodLevel.RED -> R.color.foodRed
        FoodLevel.YELLOW -> R.color.foodYellow
        FoodLevel.GREEN -> R.color.foodGreen
        else -> R.color.foodUnknown
    }

    @DrawableRes fun getFoodImage(level: FoodLevel): Int = when(level) {
        FoodLevel.RED -> R.drawable.red
        FoodLevel.YELLOW -> R.drawable.yellow
        FoodLevel.GREEN -> R.drawable.green
        else -> R.drawable.yellow
    }
}