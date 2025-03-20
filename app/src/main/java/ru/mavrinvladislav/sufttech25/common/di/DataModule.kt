package ru.mavrinvladislav.sufttech25.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mavrinvladislav.sufttech25.common.data.local.AppDatabase

@Module
interface DataModule {

    companion object {

        @ApplicationScope
        @Provides
        fun provideAppDatabase(context: Context) = AppDatabase.getInstance(context)

        @ApplicationScope
        @Provides
        fun provideFavouriteBooksDao(appDatabase: AppDatabase) = appDatabase.favouriteBooksDao()
    }
}