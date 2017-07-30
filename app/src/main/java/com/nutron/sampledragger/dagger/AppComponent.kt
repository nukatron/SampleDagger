package com.nutron.sampledragger.dagger

import com.nutron.sampledragger.presentation.foodz.FoodzViewModelImpl
import com.nutron.sampledragger.presentation.foodz.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = arrayOf(AppModule::class, NetworkModule::class, ViewModelModule::class))
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(vm: FoodzViewModelImpl)
}
