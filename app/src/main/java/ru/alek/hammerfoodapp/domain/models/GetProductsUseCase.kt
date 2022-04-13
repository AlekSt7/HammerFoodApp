package ru.alek.hammerfoodapp.domain.models

import ru.alek.hammerfoodapp.domain.models.interfaces.repo.MainRepository

class GetProductsUseCase(private val repository: MainRepository) {

    suspend fun execute() = repository.getRecipes()

}