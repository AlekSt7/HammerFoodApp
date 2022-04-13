package ru.alek.hammerfoodapp.presentation.main_scroll_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.alek.hammerfoodapp.databinding.FoodItemBinding
import ru.alek.hammerfoodapp.domain.models.Product

class ProductAdapter(private val products: List<Product>): RecyclerView.Adapter<ProductAdapter.BannerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BannerHolder(
        FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BannerHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    class BannerHolder(private val binding: FoodItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.apply {

                name.text = product.title

                Glide.with(itemView.context)
                    .load(product.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(preview)
            }
        }
    }

}