package ru.alek.hammerfoodapp.domain.models

import com.google.gson.annotations.SerializedName

data class ServerResponse(@SerializedName("recipes") val products: List<Product>)

data class Product(
    val title: String,
    val image: String)