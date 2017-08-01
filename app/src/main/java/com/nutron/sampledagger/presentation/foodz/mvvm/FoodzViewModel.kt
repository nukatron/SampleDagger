package com.nutron.sampledagger.presentation.foodz.mvvm

import com.jakewharton.rxrelay2.BehaviorRelay
import com.nutron.sampledagger.data.entity.FoodzItem
import com.nutron.sampledagger.data.network.UsdaApi
import com.nutron.sampledagger.extensions.addTo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


interface FoodzViewModelInout {
    fun getFoodz()
    fun cleanup()
}

interface FoodzViewModelOutput {
    val showProgress: Observable<Boolean>
    val foodzResult: Observable<List<FoodzItem>>
}

interface FoodzViewModel {
    val input: FoodzViewModelInout
    val output: FoodzViewModelOutput
}


//class FoodzViewModelImpl @Inject constructor(val api: UsdaApi) : FoodzViewModel, FoodzViewModelInout, FoodzViewModelOutput  {
class FoodzViewModelImpl(val api: UsdaApi) : FoodzViewModel, FoodzViewModelInout, FoodzViewModelOutput {


    val disposeBag = CompositeDisposable()

    override val input: FoodzViewModelInout = this
    override val output: FoodzViewModelOutput = this
    override val showProgress: BehaviorRelay<Boolean> = BehaviorRelay.create()
    override val foodzResult: BehaviorRelay<List<FoodzItem>> = BehaviorRelay.create()

    /** if you do like this you need to define inject method in component
     * and declare the constructor like above comment */
//    @Inject lateinit var api: UsdaApi
//    init {
//        MainApplication.appComponent.inject(this)
//    }

    override fun getFoodz() {
        api.getFoodzList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { showProgress.accept(true) }
                .map { it.list.items.filter { !it.name.contains("ERROR") } }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { showProgress.accept(false) }
                .subscribe(foodzResult, Consumer { showProgress.accept(false) })
                .addTo(disposeBag)
    }

    override fun cleanup() {
        disposeBag.clear()
    }

}