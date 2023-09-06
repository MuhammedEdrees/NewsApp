package com.edrees.newsapp.ui.categories.categorized_news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edrees.newsapp.R
import com.edrees.newsapp.databinding.HomeListItemBinding
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.ui.home.DetailsCallback

class CategorizedNewsAdapter(private val callback: DetailsCallback): RecyclerView.Adapter<CategorizedNewsAdapter.ViewHolder>(){
    private val data = mutableListOf<Article>()
    class ViewHolder(val binding: HomeListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
                    callback.navigateToDetails(this)
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