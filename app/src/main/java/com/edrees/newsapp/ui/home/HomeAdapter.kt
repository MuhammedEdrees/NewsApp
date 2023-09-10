package com.edrees.newsapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edrees.newsapp.R
import com.edrees.newsapp.databinding.MainListItemBinding
import com.edrees.newsapp.model.Article

class HomeAdapter(private val detailsCallback: DetailsCallback) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){
    private val data = mutableListOf<Article>()
    class HomeViewHolder(val binding: MainListItemBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = MainListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder){
            with(data[position]){
                binding.itemPuplisher.text = source?.name
                binding.itemDate.text = publishedAt
                binding.itemTitle.text = title
                if(urlToImage.isNullOrBlank()){
                    binding.itemImage.setImageResource(R.drawable.no_image)
                } else {
                    Glide.with(binding.root)
                        .load(urlToImage)
                        .into(binding.itemImage)
                }
                binding.root.setOnClickListener(null)
                binding.root.setOnClickListener {
                    detailsCallback.navigateToDetails(this)
                }
            }
        }
    }
    fun setData(newData: List<Article>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}