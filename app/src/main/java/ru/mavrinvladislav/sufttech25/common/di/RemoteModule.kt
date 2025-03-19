package ru.mavrinvladislav.sufttech25.common.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mavrinvladislav.sufttech25.common.util.RemoteConstants

@Module
interface RemoteModule {

    companion object {

        @Provides
        fun provideRetrofit(
            okHttpClient: OkHttpClient
        ) = Retrofit.Builder()
            .baseUrl(RemoteConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        @Provides
        fun provideOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor
        ) = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        @Provides
        fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}