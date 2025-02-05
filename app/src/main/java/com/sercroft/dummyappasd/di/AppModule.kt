package com.sercroft.dummyappasd.di

import com.sercroft.dummyappasd.data.service.JsonPlaceholderApiService
import com.sercroft.dummyappasd.data.service.RandomDogApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("JsonPlaceholder")
    fun provideRetrofitJsonPlaceHolder(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Named("Doggys")
    fun provideRetrofitDoggys(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://random.dog/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideJsonPlaceholderApiService(@Named("JsonPlaceholder") retrofit: Retrofit): JsonPlaceholderApiService =
        retrofit.create(JsonPlaceholderApiService::class.java)

    @Provides
    fun provideRandomDogApiService(@Named("Doggys") retrofit: Retrofit): RandomDogApiService =
        retrofit.create(RandomDogApiService::class.java)
}
