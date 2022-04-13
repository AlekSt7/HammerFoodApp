package ru.alek.hammerfoodapp.domain.models.interfaces.repo
import ru.alek.hammerfoodapp.domain.models.ServerResponse

interface MainRepository {

    suspend fun getRecipes(): ServerResponse?

}