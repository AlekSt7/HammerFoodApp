package ru.alek.hammerfoodapp.data.network

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alek.hammerfoodapp.App
import ru.alek.hammerfoodapp.data.network.interfaces.api.ApiService
import ru.alek.hammerfoodapp.utils.MainUtils
import java.util.concurrent.TimeUnit


object NetworkHandler {

    private val retrofit by lazy { Retrofit.Builder()
        .baseUrl(MainUtils.BASE_URL)
        .client(buildOkHttpCache())
        .addConverterFactory(GsonConverterFactory.create())
        .build() }

    /**
     * На самом деле, считаю что такое кэширование не совсем правильное, т.к. в будущем очень сложно манипулировать такой кэшированной информацией,
     * в т.ч. проводить поиск и т.п., но для данного тестового задания подходит. Как альтернатива может подойти Room или RemoteMediator с Room из Paging3
     */
    private fun buildOkHttpCache(): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(Cache(App.instance.cacheDir, 10 * 1024 * 1024))
            .addInterceptor(offlineInterceptor())
            .build()
    }

    private fun offlineInterceptor() = Interceptor {
            var request: Request = it.request()
            if (!App.hasNetwork()) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
            }
            it.proceed(request)
        }


    val api: ApiService
        get(){
            return retrofit.create(ApiService::class.java)
        }

}