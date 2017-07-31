package com.nutron.sampledagger.presentation.food.rxmvvm

import com.jakewharton.rxrelay2.PublishRelay
import com.nutron.sampledagger.base.YELLOW_LEVEL
import com.nutron.sampledagger.data.entity.Food
import com.nutron.sampledagger.data.network.UsdaApi
import com.nutron.sampledagger.extensions.elements
import com.nutron.sampledagger.extensions.error
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


interface RxFoodViewModelInput {
    val active: PublishRelay<String>
}

interface RxFoodViewModelOutput {
    val yellowReport: Observable<Food>
    val redReport: Observable<Food>
    val greenReport: Observable<Food>
    val unknownReport: Observable<Food>
    val showProgress: Observable<Boolean>
    val error: Observable<Throwable>
}

interface RxFoodViewModel {
    val input: RxFoodViewModelInput
    val output: RxFoodViewModelOutput
}

class RxFoodViewModelImpl(val api: UsdaApi) : RxFoodViewModel, RxFoodViewModelInput, RxFoodViewModelOutput {

    override val active: PublishRelay<String> = PublishRelay.create()
    override val input: RxFoodViewModelInput by RxReadOnlyProperty<RxFoodViewModelImpl, RxFoodViewModelInput>()
    override val output: RxFoodViewModelOutput by RxReadOnlyProperty<RxFoodViewModelImpl, RxFoodViewModelOutput>()
    override val yellowReport: Observable<Food>
    override val redReport: Observable<Food>
    override val greenReport: Observable<Food>
    override val unknownReport: Observable<Food>
    override val showProgress: Observable<Boolean>
    override val error: Observable<Throwable>

    init {
        val shareOutput = active
                .observeOn(Schedulers.io())
                .flatMap { api.getFoodItem(it).materialize() }
                .share()

        val startProgress = active.map { true }
        val endProgress = shareOutput.map { false }
        showProgress = Observable.merge(startProgress, endProgress)


        val successOutput = shareOutput.elements()
                .map { it.foodList.foods[0] }
                .share()

        error = shareOutput.error()

        unknownReport = successOutput.filter {
            val nutrient = it.nutrients!![0]
            nutrient.value.toDouble() < 0
        }

        greenReport = successOutput.filter {
            val nutrient = it.nutrients!![0]
            nutrient.value.toDouble() < YELLOW_LEVEL
        }

        yellowReport = successOutput.filter {
            val nutrient = it.nutrients!![0]
            nutrient.value.toDouble() < YELLOW_LEVEL
        }

        redReport = successOutput.filter {
            val nutrient = it.nutrients!![0]
            nutrient.value.toDouble() > YELLOW_LEVEL
        }
    }

    private inner class RxReadOnlyProperty<in R, out T> : ReadOnlyProperty<R, T> {
        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: R, property: KProperty<*>): T {
            return thisRef as T
        }
    }
}

