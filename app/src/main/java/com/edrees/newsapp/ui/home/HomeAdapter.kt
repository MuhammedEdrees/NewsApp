package com.edrees.newsapp.ui.home

import android.content.Context
import android.icu.util.UniversalTimeScale.toLong
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edrees.newsapp.R
import com.edrees.newsapp.databinding.MainListItemBinding
import com.edrees.newsapp.model.Article
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class HomeAdapter(private val detailsCallback: DetailsCallback, private val context: Context) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){
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
        val currentDateTime = LocalDateTime.now()
        val difference = Duration.between(date, currentDateTime).abs()

        return when {
            difference < Duration.ofMinutes(1) -> context.getString(R.string.a_few_seconds_ago)
            difference < Duration.ofHours(1) -> String.format(context.getString(R.string.home_minute_count), difference.toMinutes())
            difference < Duration.ofDays(1) -> String.format(context.getString(R.string.home_hour_count), difference.toHours())
            difference < Duration.ofDays(2) -> context.getString(R.string.home_yesterday)
            difference < Duration.ofDays(7) -> String.format(context.getString(R.string.home_last_day), getDayStringResource(date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())))
            else -> date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
        }
    }

    private fun getDayStringResource(displayName: String): String {
        return when(displayName) {
            "Saturday" -> context.getString(R.string.saturday)
            "Sunday" -> context.getString(R.string.sunday)
            "Monday" -> context.getString(R.string.monday)
            "Tuesday" -> context.getString(R.string.tuesday)
            "Wednesday" -> context.getString(R.string.wednesday)
            "Thursday" -> context.getString(R.string.thursday)
            else -> context.getString(R.string.friday)
        }
    }

    fun setData(newData: List<Article>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}