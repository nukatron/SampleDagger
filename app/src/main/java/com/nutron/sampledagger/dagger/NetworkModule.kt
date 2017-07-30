package com.nutron.sampledagger.dagger

import com.nutron.sampledagger.base.BASE_URL
import com.nutron.sampledagger.base.NAME_BASE_URL
import com.nutron.sampledagger.data.network.UsdaApi
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Named(NAME_BASE_URL)
    fun provideBaseUrlString(): String = BASE_URL

    @Provides
    @Singleton
    fun provideGsonConverter(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(converter: Converter.Factory , @Named(NAME_BASE_URL) baseUrl: String) = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converter)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): UsdaApi = retrofit.create(UsdaApi::class.java)
}