package com.edrees.newsapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edrees.newsapp.R
import com.edrees.newsapp.databinding.FragmentCategoriesBinding
import com.edrees.newsapp.model.Category

class CategoriesFragment : Fragment(), CategoryCallback {

    private var _binding: FragmentCategoriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.categoriesRecyclerView
        val data = listOf<Category>(Category(R.string.category_business, R.drawable.business),
            Category(R.string.category_entertainment, R.drawable.entertainment),
            Category(R.string.category_general, R.drawable.general),
            Category(R.string.category_health, R.drawable.health),
            Category(R.string.category_science, R.drawable.science),
            Category(R.string.category_sports, R.drawable.sports),
            Category(R.string.category_technology, R.drawable.technology))
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = CategoriesAdapter(data, this)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun navigateToCatgorizedNews(category: Category) {
        val action = CategoriesFragmentDirections.actionNavCategoriesToCategorizedNewsFragment(category)
        findNavController().navigate(action)
    }
}