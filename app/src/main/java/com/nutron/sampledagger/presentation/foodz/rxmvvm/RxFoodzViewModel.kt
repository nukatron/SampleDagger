package com.nutron.sampledagger.presentation.foodz.rxmvvm

import com.jakewharton.rxrelay2.PublishRelay
import com.nutron.sampledagger.data.entity.FoodzItem
import com.nutron.sampledagger.data.network.UsdaApi
import com.nutron.sampledagger.extensions.elements
import com.nutron.sampledagger.extensions.error
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


interface RxFoodzViewModelInput {
    val active: PublishRelay<Unit>
}

interface RxFoodzViewModelOutput {
    val showProgress: Observable<Boolean>
    val foodzResult: Observable<List<FoodzItem>>
    val error: Observable<Throwable>
}

interface RxFoodzViewModel {
    val input: RxFoodzViewModelInput
    val output: RxFoodzViewModelOutput
}

class RxFoodzViewModelImpl(api: UsdaApi) : RxFoodzViewModel, RxFoodzViewModelInput, RxFoodzViewModelOutput {

    override val input: RxFoodzViewModelInput by RxReadOnlyProperty<RxFoodzViewModel, RxFoodzViewModelInput>()
    override val output: RxFoodzViewModelOutput by RxReadOnlyProperty<RxFoodzViewModel, RxFoodzViewModelOutput>()

    override val active: PublishRelay<Unit> = PublishRelay.create()
    override val showProgress: Observable<Boolean>
    override val foodzResult: Observable<List<FoodzItem>>
    override val error: Observable<Throwable>

    init {
        val shareOutput = active
                .observeOn(Schedulers.io())
                .flatMap { api.getFoodzList().materialize() }
                .share()

        // init show progress observable
        val startProgress = active.map { true }
        val stopProgress = shareOutput.map { false }

        // init final output
        error = shareOutput.error()
        showProgress = Observable.merge(startProgress, stopProgress)
        foodzResult = shareOutput
                .elements()
                .map { it.list.items.filter { !it.name.contains("ERROR") } }
    }

    @Suppress("UNCHECKED_CAST")
    private inner class RxReadOnlyProperty<in R, out T> : ReadOnlyProperty<R, T> {
        override fun getValue(thisRef: R, property: KProperty<*>): T {
            return thisRef as T
        }

    }
}

