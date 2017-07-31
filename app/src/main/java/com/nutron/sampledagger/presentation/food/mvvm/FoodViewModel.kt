package com.nutron.sampledagger.presentation.food.mvvm

import android.util.Log
import com.jakewharton.rxrelay2.BehaviorRelay
import com.nutron.sampledagger.base.RED_LEVEL
import com.nutron.sampledagger.base.YELLOW_LEVEL
import com.nutron.sampledagger.data.entity.Food
import com.nutron.sampledagger.data.network.UsdaApi
import com.nutron.sampledagger.extensions.addTo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


interface FoodViewModelInput {
    fun getFood(foodId: String)
    fun cleanup()
}

interface FoodViewModelOutput {
    val yellowReport: Observable<Food>
    val redReport: Observable<Food>
    val greenReport: Observable<Food>
    val unknownReport: Observable<Food>
    val showProgress: Observable<Boolean>
    val error: Observable<Throwable>
}

interface FoodViewModel {
    val input: FoodViewModelInput
    val output: FoodViewModelOutput
}

class FoodViewModelImpl(val api: UsdaApi): FoodViewModel, FoodViewModelInput, FoodViewModelOutput {

    override val input: FoodViewModelInput by RxReadOnlyProperty<FoodViewModelImpl, FoodViewModelInput>()
    override val output: FoodViewModelOutput by RxReadOnlyProperty<FoodViewModelImpl, FoodViewModelOutput>()
    override val yellowReport: BehaviorRelay<Food> = BehaviorRelay.create()
    override val redReport: BehaviorRelay<Food> = BehaviorRelay.create()
    override val greenReport: BehaviorRelay<Food> = BehaviorRelay.create()
    override val unknownReport: BehaviorRelay<Food> = BehaviorRelay.create()
    override val showProgress: BehaviorRelay<Boolean> = BehaviorRelay.create()
    override val error: BehaviorRelay<Throwable> = BehaviorRelay.create()

    val disposeBag = CompositeDisposable()

    override fun getFood(foodId: String) {
        api.getFoodItem(foodId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { showProgress.accept(true) }
                .doOnComplete { showProgress.accept(false) }
                .map { it.foodList.foods[0] }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ food ->
                    handleOutput(food)
                }, { e ->
                    showProgress.accept(false)
                    error.accept(e)
                }).addTo(disposeBag)
    }

    override fun cleanup() {
        disposeBag.clear()
    }

    private fun handleOutput(food: Food) {
        val nutrients = food.nutrients
        nutrients?.let {
            val nutrient = it[0]
            try {
                val nutrientValue = nutrient.value.toDouble()
                if (nutrientValue < 0) {
                    unknownReport.accept(food)
                } else if (nutrientValue < YELLOW_LEVEL) {
                    greenReport.accept(food)
                } else if (nutrientValue < RED_LEVEL) {
                    yellowReport.accept(food)
                } else {
                    redReport.accept(food)
                }
            } catch (e: NumberFormatException) {
                Log.e("ResourceManager", "Error parsing nutrient value")
            }
        } ?: unknownReport.accept(food)
    }

    private inner class RxReadOnlyProperty<in R, out T> : ReadOnlyProperty<R, T> {
        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: R, property: KProperty<*>): T {
            return thisRef as T
        }
    }
}

