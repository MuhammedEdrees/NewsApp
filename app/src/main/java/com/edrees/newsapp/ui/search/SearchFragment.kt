package com.edrees.newsapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edrees.newsapp.databinding.FragmentSearchBinding
import com.edrees.newsapp.local.LocalSourceImpl
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.network.APIClient
import com.edrees.newsapp.repo.ArticleRepositoryImpl
import com.edrees.newsapp.ui.ViewModelFactory
import com.edrees.newsapp.ui.home.DetailsCallback
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment(), DetailsCallback {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var searchEditText: TextInputEditText
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        searchEditText = binding.searchTextInputLayout.editText as TextInputEditText
        viewModel.listOfArticles.observe(viewLifecycleOwner){articles ->
            if(searchEditText.text.isNullOrBlank()){
                adapter.setData(listOf())
            } else {
                adapter.setData(articles)
            }
        }
        recyclerView = binding.searchRecyclerView
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = SearchAdapter(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        searchEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isBlank()){
                    adapter.setData(listOf<Article>())
                } else {
                    viewModel.search(p0.toString(), "en", 1)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun prepareViewModel() {
        val factory = ViewModelFactory(ArticleRepositoryImpl(APIClient, LocalSourceImpl(requireContext())))
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
    }

    override fun onDestroyView() {
        adapter.setData(listOf())
        super.onDestroyView()
        _binding = null
    }

    override fun navigateToDetails(article: Article) {
        val action = SearchFragmentDirections.actionNavSearchToDetailsFragment(article)
        findNavController().navigate(action)
    }
}