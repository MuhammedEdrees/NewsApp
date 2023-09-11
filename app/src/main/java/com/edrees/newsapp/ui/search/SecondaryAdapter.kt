package com.edrees.newsapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.edrees.newsapp.databinding.SecondaryListItemBinding
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.ui.home.DetailsCallback
import jp.wasabeef.glide.transformations.BlurTransformation

open class SecondaryAdapter(private val callback: DetailsCallback): RecyclerView.Adapter<SecondaryAdapter.ViewHolder>() {
    private val data = mutableListOf<Article>()
    inner class ViewHolder(val binding: SecondaryListItemBinding): RecyclerView.ViewHolder(binding.root) {
        init{
            binding.root.setOnClickListener {
                this@SecondaryAdapter.callback.navigateToDetails(data[layoutPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SecondaryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(data[position]){
                binding.titleTextView.text = title
                binding.descriptionTextView.text = description
                binding.sourceTextView.text = source?.name?:"Unknown Source"
                Glide.with(binding.root)
                    .load(urlToImage)
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(5, 1)))
                    .into(binding.itemThumbnail)
            }
        }
    }
    fun setData(newData: List<Article>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    fun removeItem(pos: Int){
        data.removeAt(pos)
        notifyItemRemoved(pos)
    }
}