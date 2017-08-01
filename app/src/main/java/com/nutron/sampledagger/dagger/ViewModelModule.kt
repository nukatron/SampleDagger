package com.nutron.sampledagger.dagger

import com.nutron.sampledagger.data.network.UsdaApi
import com.nutron.sampledagger.presentation.food.mvvm.FoodViewModel
import com.nutron.sampledagger.presentation.food.mvvm.FoodViewModelImpl
import com.nutron.sampledagger.presentation.food.rxmvvm.RxFoodViewModel
import com.nutron.sampledagger.presentation.food.rxmvvm.RxFoodViewModelImpl
import com.nutron.sampledagger.presentation.foodz.mvvm.FoodzViewModel
import com.nutron.sampledagger.presentation.foodz.mvvm.FoodzViewModelImpl
import com.nutron.sampledagger.presentation.foodz.rxmvvm.RxFoodzViewModel
import com.nutron.sampledagger.presentation.foodz.rxmvvm.RxFoodzViewModelImpl
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

    @Provides
    fun provideRxFoodzViewModel(api: UsdaApi): RxFoodzViewModel = RxFoodzViewModelImpl(api)
}