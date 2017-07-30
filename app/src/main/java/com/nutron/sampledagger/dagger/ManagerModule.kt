package com.nutron.sampledagger.dagger

import com.nutron.sampledagger.domain.ResourceManager
import dagger.Module
import dagger.Provides


@Module
class ManagerModule {

    @Provides
    fun provideResourceManager(): ResourceManager = ResourceManager()
}