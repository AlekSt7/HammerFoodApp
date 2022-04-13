package ru.alek.hammerfoodapp.data.network.repositories

import ru.alek.hammerfoodapp.data.network.interfaces.api.ApiService
import ru.alek.hammerfoodapp.domain.models.ServerResponse
import ru.alek.hammerfoodapp.domain.models.interfaces.repo.MainRepository

class MainRepoImpl(private val api: ApiService): MainRepository {
    override suspend fun getRecipes(): ServerResponse? {
        try {
            val r = api.loadRandomRecipes()
            if(r.isSuccessful){
                if(r.code() in 200..399){
                    return r.body()
                }else{
                    throw Exception("http code: ${r.code()}")
                }
            }else{
                throw Exception(r.errorBody().toString())
            }
        }catch(e: Exception){
            throw e
        }
    }
}