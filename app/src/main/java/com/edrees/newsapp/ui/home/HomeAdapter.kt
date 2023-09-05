package com.edrees.newsapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edrees.newsapp.databinding.HomeListItemBinding
import com.edrees.newsapp.model.Article

class HomeAdapter(private val homeCallback: HomeCallback) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){
    private val data = mutableListOf<Article>()
    class HomeViewHolder(val binding: HomeListItemBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = HomeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder){
            with(data[position]){
                binding.itemPuplisher.text = source.name
                binding.itemDate.text = publishedAt
                binding.itemTitle.text = title
                Glide.with(binding.root)
                    .load(urlToImage)
                    .into(binding.itemImage)
                binding.root.setOnClickListener {
                    homeCallback.navigateToDetails(this)
                }
            }
        }
    }
}