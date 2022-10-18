package com.enraher.githublist.di

import com.enraher.githublist.BuildConfig
import com.enraher.githublist.data.remoto.CommonApiInterceptor
import com.enraher.githublist.data.remoto.service.ApiWebService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiWebService = retrofit.create(ApiWebService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val timeoutInSeconds = 120
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        val okHttpBuilder = OkHttpClient.Builder()
            .connectTimeout(timeoutInSeconds.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeoutInSeconds.toLong(), TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .addInterceptor(CommonApiInterceptor())

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(interceptor)
        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}