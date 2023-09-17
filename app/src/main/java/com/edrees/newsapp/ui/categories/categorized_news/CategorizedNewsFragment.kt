package com.edrees.newsapp.ui.categories.categorized_news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.edrees.newsapp.MainActivity
import com.edrees.newsapp.R
import com.edrees.newsapp.databinding.FragmentCategoriesBinding
import com.edrees.newsapp.databinding.FragmentCategorizedNewsBinding
import com.edrees.newsapp.local.LocalSourceImpl
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.network.APIClient
import com.edrees.newsapp.repo.ArticleRepositoryImpl
import com.edrees.newsapp.ui.ViewModelFactory
import com.edrees.newsapp.ui.home.DetailsCallback
import com.edrees.newsapp.ui.home.HomeAdapter
import com.edrees.newsapp.ui.home.HomeFragmentDirections
import com.edrees.newsapp.util.ConnectionUtils
import com.edrees.newsapp.util.ConnectionUtils.recreateFragment

class CategorizedNewsFragment : Fragment(), DetailsCallback {
    private lateinit var recyclerView: RecyclerView
    private val args: CategorizedNewsFragmentArgs by navArgs()
    private var _binding: FragmentCategorizedNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CategorizedNewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategorizedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!ConnectionUtils.checkInternetConnection(activity)) {
            showNoInternetConnectionLayout()
        } else {
            hideNoInternetConnectionLayout()
            prepareViewModel()
            val adapter = HomeAdapter(this, requireContext())
            recyclerView = binding.categorizedNewsRecyclerView
            recyclerView.adapter = adapter
            viewModel.listOfArticles.observe(viewLifecycleOwner){
                adapter.setData(it)
            }
            viewModel.getCategorizedArticles(resources.getString(args.category.nameRes).lowercase())
        }
        setFragmentTitle(String.format(resources.getString(R.string.news_category_title), resources.getString(args.category.nameRes)))
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

    private fun setFragmentTitle(title: String) {
        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = title
    }

    private fun prepareViewModel() {
        val factory = ViewModelFactory(ArticleRepositoryImpl(APIClient, LocalSourceImpl(requireContext())))
        viewModel = ViewModelProvider(this, factory).get(CategorizedNewsViewModel::class.java)
    }

    override fun navigateToDetails(article: Article) {
        val action = CategorizedNewsFragmentDirections.actionCategorizedNewsFragmentToDetailsFragment(article)
        findNavController().navigate(action)
    }
}