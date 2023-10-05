package com.edrees.newsapp.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edrees.newsapp.databinding.FragmentBookmarksBinding
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.ui.home.DetailsCallback
import com.edrees.newsapp.ui.search.SecondaryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarksFragment : Fragment(), DetailsCallback {
    private  val viewModel by viewModel<BookmarksViewModel>()
    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun navigateToDetails(article: Article) {
        val action = BookmarksFragmentDirections.actionNavFavoritesToDetailsFragment(article)
        findNavController().navigate(action)
    }
}