package com.nutron.sampledragger.dagger

import com.nutron.sampledragger.data.network.UsdaApi
import com.nutron.sampledragger.presentation.foodz.FoodzViewModel
import com.nutron.sampledragger.presentation.foodz.FoodzViewModelImpl
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule {
    
    @Provides
    fun provideFoodzViewModel(api: UsdaApi): FoodzViewModel = FoodzViewModelImpl()
}