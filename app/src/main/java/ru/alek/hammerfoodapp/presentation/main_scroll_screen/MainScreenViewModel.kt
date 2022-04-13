package ru.alek.hammerfoodapp.presentation.main_scroll_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alek.hammerfoodapp.App
import ru.alek.hammerfoodapp.R
import ru.alek.hammerfoodapp.data.network.repositories.MainRepoImpl
import ru.alek.hammerfoodapp.domain.models.GetProductsUseCase
import ru.alek.hammerfoodapp.domain.models.Product
import java.lang.Exception

class MainScreenViewModel : ViewModel() {

    private val api = App.instance.getApi()
    private val repo = MainRepoImpl(api)
    private val getProductsUseCase = GetProductsUseCase(repo)

    companion object {
        const val LOADING: Byte = 1
        const val LOADED: Byte = 2
        const val ERROR: Byte = 3
    }

    //LiveData
    private val bannersMutableLiveData = MutableLiveData<Array<Int>>()
    val bannersLiveData: LiveData<Array<Int>> = bannersMutableLiveData

    private val productsMutableLiveData = MutableLiveData<List<Product>>()
    val productsLiveData: LiveData<List<Product>> = productsMutableLiveData

    private val uiStateMutableLiveData = MutableLiveData(LOADING)
    val uiStateLiveData = uiStateMutableLiveData

    //Захардкодил картинки напрямую
    fun fetchBanners(){
        val banners = arrayOf(R.drawable.banner_1,
            R.drawable.banner_2,
            R.drawable.banner_3)
        bannersMutableLiveData.value = banners
    }

    fun fetchRecipes(){
        uiStateMutableLiveData.value = LOADING
        viewModelScope.launch {
            try {
                productsMutableLiveData.postValue(getProductsUseCase.execute()?.products)
                uiStateMutableLiveData.postValue(LOADED)
            }catch(e: Exception){
                uiStateMutableLiveData.postValue(ERROR)
            }
        }
    }

}