package com.itheamc.newsfeedappnchl.ui.fragments.news_section

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.itheamc.newsfeedappnchl.NCHLApplication
import com.itheamc.newsfeedappnchl.data.viewmodels.NewsFeedViewModel
import com.itheamc.newsfeedappnchl.data.viewmodels.NewsFeedViewModelFactory
import com.itheamc.newsfeedappnchl.databinding.FragmentNewsSectionBinding
import kotlinx.coroutines.launch

class NewsSectionFragment : Fragment() {

    // FragmentNewsSectionBinding
    private var _binding: FragmentNewsSectionBinding? = null

    private val binding get() = _binding!!

    // NewsSectionViewModel
    private lateinit var viewModel: NewsSectionViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initializing FragmentNewsSectionBinding
        _binding = FragmentNewsSectionBinding.inflate(inflater, container, false)

        // Initializing News Section View Model
        viewModel =
            ViewModelProvider(this)[NewsSectionViewModel::class.java]

        Log.d("AMIT", "onCreateView: ")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("AMIT", "onViewCreated: ")

        /**
         * Getting the main repository instance from the application
         */
        val repository = (requireActivity().application as NCHLApplication).newsFeedRepository

        /**
         * Initializing View model with repository instance
         */
        val viewModel: NewsFeedViewModel by viewModels(factoryProducer = {
            NewsFeedViewModelFactory(
                repository
            )
        })

        lifecycleScope.launch {
            viewModel.fetch()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}