package com.edrees.newsapp.ui.categories.categorized_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.edrees.newsapp.R
import com.edrees.newsapp.databinding.FragmentCategorizedNewsBinding
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.ui.home.DetailsCallback
import com.edrees.newsapp.ui.home.HomeAdapter
import com.edrees.newsapp.util.ConnectionUtils
import com.edrees.newsapp.util.ConnectionUtils.recreateFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategorizedNewsFragment : Fragment(), DetailsCallback {
    private lateinit var recyclerView: RecyclerView
    private val args: CategorizedNewsFragmentArgs by navArgs()
    private var _binding: FragmentCategorizedNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<CategorizedNewsViewModel>()
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

    override fun navigateToDetails(article: Article) {
        val action = CategorizedNewsFragmentDirections.actionCategorizedNewsFragmentToDetailsFragment(article)
        findNavController().navigate(action)
    }
}