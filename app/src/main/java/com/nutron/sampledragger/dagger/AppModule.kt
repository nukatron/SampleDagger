package com.nutron.sampledragger.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application
}