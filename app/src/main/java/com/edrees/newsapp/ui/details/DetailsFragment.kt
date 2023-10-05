package com.edrees.newsapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.edrees.newsapp.R
import com.edrees.newsapp.databinding.FragmentDetailsBinding
import com.edrees.newsapp.model.Article
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern
import java.time.format.TextStyle
import java.util.Locale

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel by viewModel<DetailsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article
        bindAllViews(article)
        setFragmentTitle("Article Details")
    }

    private fun setFragmentTitle(title: String) {
        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = title
    }

    private fun bindAllViews(article: Article){
        binding.sourceTextview.bindNullableText(article.source?.name, "Source: ")
        binding.titleTextview.bindNullableText(article.title)
        binding.authorTextview.bindNullableText(article.author, "By: ")
        val articleDate = LocalDateTime.parse(article.publishedAt, ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
        with(articleDate){
            binding.timeTextview.bindNullableText(String.format(resources.getString(R.string.details_date_template),
                dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                dayOfMonth,
                year,
                timeTo12HFormat(hour, minute)), "Puplished at: ")
        }
        binding.contentTextview.bindNullableText(article.content)
        if(article.urlToImage.isNullOrBlank()){
            binding.thumbnailImageView.setImageResource(R.drawable.no_image3)
        } else {
            Glide.with(binding.root)
                .load(article.urlToImage)
                .into(binding.thumbnailImageView)
        }
        viewModel.checkIfFavorite(article)
        viewModel.isFavorite.observe(viewLifecycleOwner){
            binding.floatingActionButton.setImageResource(if(it) R.drawable.ic_star else R.drawable.ic_outlined_star)
            binding.floatingActionButton.setOnClickListener {
                if(viewModel.isFavorite.value!!){
                    viewModel.deleteFavorite(article)
                } else {
                    viewModel.addFavorite(article)
                }
            }
        }
        binding.readMoreButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(intent)
        }
        binding.shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, article.url)
            startActivity(Intent.createChooser(shareIntent, "Share using"))
        }

    }
    private fun TextView.bindNullableText(text: String?, prefix: String = ""){
        if(text.isNullOrBlank()){
            this.visibility = View.GONE
        } else {
            this.text = String.format(resources.getString(R.string.details_template), prefix, text)
        }
    }
    private fun timeTo12HFormat(h: Int, m: Int): String {
        val postfix = if (h <= 11) "AM" else "PM"
        val hour = if(h == 0) 12 else h%12
        return String.format("%02d:%02d %s", hour, m, postfix)
    }
}