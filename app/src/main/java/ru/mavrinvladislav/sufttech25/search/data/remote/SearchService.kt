package ru.mavrinvladislav.sufttech25.search.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mavrinvladislav.sufttech25.search.data.remote.model.FetchResponse

interface SearchService {

    @GET("volumes")
    suspend fun fetchSearch(
        @Query("q")
        query: String,
        @Query("path")
        page: Int
    ): Response<FetchResponse>
}