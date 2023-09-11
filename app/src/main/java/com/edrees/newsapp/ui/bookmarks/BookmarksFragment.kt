package com.edrees.newsapp.ui.bookmarks

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edrees.newsapp.R
import com.edrees.newsapp.databinding.FragmentBookmarksBinding
import com.edrees.newsapp.databinding.FragmentSearchBinding
import com.edrees.newsapp.local.LocalSourceImpl
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.network.APIClient
import com.edrees.newsapp.repo.ArticleRepositoryImpl
import com.edrees.newsapp.ui.ViewModelFactory
import com.edrees.newsapp.ui.home.DetailsCallback
import com.edrees.newsapp.ui.search.SecondaryAdapter

class BookmarksFragment : Fragment(), DetailsCallback {
    private lateinit var viewModel: BookmarksViewModel
    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        val adapter = SecondaryAdapter(this)
        var isChanged = false
        binding.bookmarksRecyclerView.apply{
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        val swipeToDeleteCallback = object: SwipeToDeleteCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                isChanged = true
                adapter.removeItem(position)
                viewModel.deleteBookmark(position)
            }
        }
        val touchHelper = ItemTouchHelper(swipeToDeleteCallback)
        touchHelper.attachToRecyclerView(binding.bookmarksRecyclerView)
        viewModel.listOfArticles.observe(viewLifecycleOwner){
            if(!isChanged){
                isChanged = false
                adapter.setData(it)
            }
        }
        viewModel.getBookmarks()
    }

    private fun prepareViewModel() {
        val factory = ViewModelFactory(ArticleRepositoryImpl(APIClient, LocalSourceImpl(requireContext())))
        viewModel = ViewModelProvider(this, factory).get(BookmarksViewModel::class.java)
    }

    override fun navigateToDetails(article: Article) {
        val action = BookmarksFragmentDirections.actionNavFavoritesToDetailsFragment(article)
        findNavController().navigate(action)
    }
}