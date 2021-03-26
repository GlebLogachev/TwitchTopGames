package com.glogachev.twitchtopgames.di.modules

import com.glogachev.twitchtopgames.data.retrofit.CustomAdapterFactory
import com.glogachev.twitchtopgames.data.retrofit.TopGamesApi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    private val BASE_URL = "https://api.twitch.tv/"

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideHttpClientFactory(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideCallAdapter(): CustomAdapterFactory = CustomAdapterFactory.createWithScheduler(Schedulers.io()) as CustomAdapterFactory


    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, adapterFactory: CustomAdapterFactory): Retrofit {
        return Retrofit
            .Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(adapterFactory)
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): TopGamesApi {
        return retrofit.create(TopGamesApi::class.java)
    }


}















