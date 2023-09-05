package com.edrees.newsapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.edrees.newsapp.R
import com.edrees.newsapp.databinding.FragmentHomeBinding
import com.edrees.newsapp.db.NewsDatabase
import com.edrees.newsapp.local.LocalSourceImpl
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.network.APIClient
import com.edrees.newsapp.repo.ArticleRepositoryImpl
import com.edrees.newsapp.ui.ViewModelFactory
import com.edrees.newsapp.ui.details.DetailsFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), HomeCallback {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        recyclerView = binding.homeRecyclerView
        val adapter = HomeAdapter(this)
        recyclerView.adapter = adapter
        viewModel.listOfArticle.observe(viewLifecycleOwner){
            adapter.setData(it)
        }
        viewModel.getTopHeadlines()
    }

    private fun prepareViewModel() {
        val factory = ViewModelFactory(ArticleRepositoryImpl(APIClient, LocalSourceImpl(requireContext())))
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun navigateToDetails(article: Article) {
        val action = HomeFragmentDirections.actionNavHomeToDetailsFragment(article)
        findNavController().navigate(action)
    }
}