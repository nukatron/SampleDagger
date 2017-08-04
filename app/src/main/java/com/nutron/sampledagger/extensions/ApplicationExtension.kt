package com.nutron.sampledagger.extensions

import android.content.Context
import android.support.v4.app.Fragment
import com.nutron.sampledagger.MainApplication
import com.nutron.sampledagger.dagger.AppComponent


val Context.component: AppComponent
    get() = MainApplication.appComponent

val Fragment.component: AppComponent
    get() = MainApplication.appComponent