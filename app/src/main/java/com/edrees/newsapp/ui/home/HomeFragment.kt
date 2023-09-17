package com.edrees.newsapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edrees.newsapp.MainActivity
import com.edrees.newsapp.databinding.FragmentHomeBinding
import com.edrees.newsapp.local.LocalSourceImpl
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.network.APIClient
import com.edrees.newsapp.repo.ArticleRepositoryImpl
import com.edrees.newsapp.ui.ViewModelFactory
import com.edrees.newsapp.ui.search.SecondaryAdapter
import com.edrees.newsapp.util.ConnectionUtils.checkInternetConnection
import com.edrees.newsapp.util.ConnectionUtils.recreateFragment
import com.edrees.newsapp.util.Constants
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment(), DetailsCallback {

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
        if(!checkInternetConnection(activity)) {
            showNoInternetConnectionLayout()
        } else {
            hideNoInternetConnectionLayout()
            prepareViewModel()
            recyclerView = binding.homeRecyclerView
            val adapter = HomeAdapter(this, context!!)
            recyclerView.adapter = adapter
            viewModel.listOfArticle.observe(viewLifecycleOwner){
                adapter.setData(it)
            }
            viewModel.getTopHeadlines()
        }

    }

    private fun hideNoInternetConnectionLayout() {
        binding.contentMain.visibility = View.VISIBLE
        binding.noInternetLayout.visibility = View.GONE
    }

    private fun showNoInternetConnectionLayout() {
        binding.noInternetLayout.visibility = View.VISIBLE
        binding.contentMain.visibility = View.GONE
        binding.retryButton.setOnClickListener{
            this.recreateFragment()
        }
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