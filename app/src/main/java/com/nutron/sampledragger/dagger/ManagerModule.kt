package com.nutron.sampledragger.dagger

import com.nutron.sampledragger.domain.ResourceManager
import dagger.Module
import dagger.Provides


@Module
class ManagerModule {

    @Provides
    fun provideResourceManager(): ResourceManager = ResourceManager()
}