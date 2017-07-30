package com.nutron.sampledagger.dagger

import com.nutron.sampledagger.presentation.food.FoodDetailActivity
import com.nutron.sampledagger.presentation.foodz.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = arrayOf(AppModule::class, NetworkModule::class, ViewModelModule::class, ManagerModule::class))
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: FoodDetailActivity)
}