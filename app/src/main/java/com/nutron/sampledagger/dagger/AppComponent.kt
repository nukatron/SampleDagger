package com.nutron.sampledagger.dagger

import com.nutron.sampledagger.presentation.food.mvvm.FoodDetailActivity
import com.nutron.sampledagger.presentation.food.rxmvvm.RxFoodDetailActivity
import com.nutron.sampledagger.presentation.foodz.mvvm.FoodzActivity
import com.nutron.sampledagger.presentation.foodz.rxmvvm.RxFoodzActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = arrayOf(AppModule::class, NetworkModule::class, ViewModelModule::class, ManagerModule::class))
interface AppComponent {
    fun inject(activity: FoodzActivity)
    fun inject(activity: RxFoodzActivity)
    fun inject(activity: FoodDetailActivity)
    fun inject(activity: RxFoodDetailActivity)
}
