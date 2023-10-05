package com.edrees.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.edrees.newsapp.databinding.FragmentHomeBinding
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.util.ConnectionUtils.checkInternetConnection
import com.edrees.newsapp.util.ConnectionUtils.recreateFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), DetailsCallback {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModel<HomeViewModel>()
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
            recyclerView = binding.homeRecyclerView
            val adapter = HomeAdapter(this, requireContext())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun navigateToDetails(article: Article) {
        val action = HomeFragmentDirections.actionNavHomeToDetailsFragment(article)
        findNavController().navigate(action)
    }
}