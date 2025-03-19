package ru.mavrinvladislav.sufttech25.search.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.mavrinvladislav.sufttech25.search.data.remote.SearchService
import ru.mavrinvladislav.sufttech25.search.data.repository.SearchRepositoryImpl
import ru.mavrinvladislav.sufttech25.search.domain.repository.SearchRepository

@Module
interface SearchModule {

    @Binds
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    companion object {

        @Provides
        fun provideSearchService(retrofit: Retrofit) = retrofit.create(SearchService::class.java)
    }
}