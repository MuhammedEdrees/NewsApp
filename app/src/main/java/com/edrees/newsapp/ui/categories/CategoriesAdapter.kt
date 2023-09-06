package com.edrees.newsapp.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.edrees.newsapp.databinding.CategoryItemBinding
import com.edrees.newsapp.databinding.HomeListItemBinding
import com.edrees.newsapp.model.Category
import jp.wasabeef.glide.transformations.BlurTransformation

class CategoriesAdapter(private val data: List<Category>, private val callback: CategoryCallback) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    class CategoriesViewHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        with(holder){
            with(data[position]){
                binding.categoryTitle.text = binding.root.resources.getString(nameRes)
                Glide.with(binding.root)
                    .load(imageRes)
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(10, 2)))
                    .into(binding.categoryThumbnail)
                binding.root.setOnClickListener(null)
                binding.root.setOnClickListener {
                    callback.navigateToCatgorizedNews(this)
                }
            }
        }
    }

}
