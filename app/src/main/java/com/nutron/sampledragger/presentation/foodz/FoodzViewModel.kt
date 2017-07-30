package com.nutron.sampledragger.presentation.foodz

import com.jakewharton.rxrelay2.BehaviorRelay
import com.nutron.sampledragger.data.entity.FoodzItem
import com.nutron.sampledragger.data.network.UsdaApi
import com.nutron.sampledragger.extensions.addTo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


interface FoodzViewModel {
    val showProgress: Observable<Boolean>
    val foodzResult: Observable<List<FoodzItem>>
    fun getFoodz()
    fun cleanup()
}


//class FoodzViewModelImpl @Inject constructor(val api: UsdaApi) : FoodzViewModel {
class FoodzViewModelImpl(val api: UsdaApi) : FoodzViewModel {

    val disposeBag = CompositeDisposable()

    override val showProgress: BehaviorRelay<Boolean> = BehaviorRelay.create()
    override val foodzResult: BehaviorRelay<List<FoodzItem>> = BehaviorRelay.create()

    //if you do like this you need to define inject method in component
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