package ru.alek.hammerfoodapp.presentation.main_scroll_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.alek.hammerfoodapp.R
import ru.alek.hammerfoodapp.databinding.MainScreenFragmentBinding
import ru.alek.hammerfoodapp.domain.models.Product
import ru.alek.hammerfoodapp.presentation.main_scroll_screen.adapters.BannerAdapter
import ru.alek.hammerfoodapp.presentation.main_scroll_screen.adapters.ProductAdapter


class MainScreen : Fragment(R.layout.main_screen_fragment) {

    private lateinit var viewModel: MainScreenViewModel
    private lateinit var binding: MainScreenFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainScreenFragmentBinding.inflate(inflater)
        binding.retryButton.setOnClickListener {
            viewModel.fetchRecipes()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainScreenViewModel::class.java]
        observeData()
        viewModel.fetchBanners()
        viewModel.fetchRecipes()
    }

    private fun observeData(){
        viewModel.bannersLiveData.observe(viewLifecycleOwner){
            bindBanners(it)
        }
        viewModel.productsLiveData.observe(viewLifecycleOwner){
            bindProducts(it)
        }
        viewModel.uiStateLiveData.observe(viewLifecycleOwner){
            setUiState(it)
        }
    }

    private fun bindBanners(images: Array<Int>){
        binding.pizzaBanners.adapter = BannerAdapter(images)
    }

    private fun bindProducts(products: List<Product>){
        binding.foodContent.adapter = ProductAdapter(products)
    }

    private fun setUiState(uiState: Byte){
        binding.apply {
            when(uiState){
                MainScreenViewModel.LOADING ->{
                    foodContent.visibility = View.GONE
                    errorWrapper.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                MainScreenViewModel.LOADED ->{
                    progressBar.visibility = View.GONE
                    errorWrapper.visibility = View.GONE
                    foodContent.visibility = View.VISIBLE
                }
                MainScreenViewModel.ERROR ->{
                    progressBar.visibility = View.GONE
                    foodContent.visibility = View.GONE
                    errorWrapper.visibility = View.VISIBLE
                }
            }
        }
    }

}