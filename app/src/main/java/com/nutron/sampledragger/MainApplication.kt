package com.nutron.sampledragger

import android.app.Application
import com.nutron.sampledragger.dagger.AppComponent
import com.nutron.sampledragger.dagger.AppModule
import com.nutron.sampledragger.dagger.DaggerAppComponent


class MainApplication: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(application: MainApplication): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(application))
                .build()
    }
}