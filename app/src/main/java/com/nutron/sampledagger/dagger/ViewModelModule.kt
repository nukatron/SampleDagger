package com.nutron.sampledagger.dagger

import com.nutron.sampledagger.data.network.UsdaApi
import com.nutron.sampledagger.presentation.food.mvvm.FoodViewModel
import com.nutron.sampledagger.presentation.food.mvvm.FoodViewModelImpl
import com.nutron.sampledagger.presentation.food.rxmvvm.RxFoodViewModel
import com.nutron.sampledagger.presentation.food.rxmvvm.RxFoodViewModelImpl
import com.nutron.sampledagger.presentation.foodz.FoodzViewModel
import com.nutron.sampledagger.presentation.foodz.FoodzViewModelImpl
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule {

    @Provides
    fun provideFoodzViewModel(api: UsdaApi): FoodzViewModel = FoodzViewModelImpl(api)

    @Provides
    fun provideFoodViewModel(api: UsdaApi): FoodViewModel = FoodViewModelImpl(api)

    @Provides
    fun provideRxFoodViewModel(api: UsdaApi): RxFoodViewModel = RxFoodViewModelImpl(api)
}