package com.nutron.sampledagger

import android.app.Application
import com.nutron.sampledagger.dagger.AppComponent
import com.nutron.sampledagger.dagger.AppModule
import com.nutron.sampledagger.dagger.DaggerAppComponent


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