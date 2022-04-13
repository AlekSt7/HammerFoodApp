package ru.alek.hammerfoodapp.data.network.interfaces.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.alek.hammerfoodapp.domain.models.ServerResponse
import ru.alek.hammerfoodapp.utils.MainUtils

interface ApiService {

    @GET("random")
    suspend fun loadRandomRecipes(@Query("apiKey") apiKey: String = MainUtils.API_KEY,
                                  @Query("number") number: Int = 15): Response<ServerResponse>

}