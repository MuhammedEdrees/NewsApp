package com.edrees.newsapp.ui.home

import android.icu.util.UniversalTimeScale.toLong
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edrees.newsapp.R
import com.edrees.newsapp.databinding.MainListItemBinding
import com.edrees.newsapp.model.Article
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

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
                binding.itemDate.text = formatHomeDate(LocalDateTime.parse(publishedAt,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))
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

    private fun formatHomeDate(date: LocalDateTime): String {
        val currentDate = LocalDateTime.now()
        return if(date.isAfter(currentDate.minusDays(1))){
            if(date.isAfter(currentDate.minusHours(1))) {
                if(date.isAfter(currentDate.minusMinutes(1))) {
                    "${currentDate.minusSeconds(date.second.toLong()).second} Seconds Ago"
                } else {
                    "${currentDate.minusMinutes(date.minute.toLong()).minute} Minutes Ago"
                }
            } else {
                "${currentDate.minusHours(date.hour.toLong()).hour} Hours Ago"
            }
        } else if(date.isAfter(currentDate.minusWeeks(1))) {
            if(currentDate.minusDays(date.dayOfMonth.toLong()).dayOfMonth >= 2)
                "Yesterday"
            else
                "Last ${date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())}"
        } else {
            "${date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${date.dayOfMonth}, ${date.year}"
        }
    }

    fun setData(newData: List<Article>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}