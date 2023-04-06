package com.itheamc.newsfeedappnchl.ui.fragments.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itheamc.newsfeedappnchl.NCHLApplication
import com.itheamc.newsfeedappnchl.R
import com.itheamc.newsfeedappnchl.adapters.NewsRecyclerViewAdapter
import com.itheamc.newsfeedappnchl.data.models.ApiResult
import com.itheamc.newsfeedappnchl.data.viewmodels.NewsFeedViewModel
import com.itheamc.newsfeedappnchl.data.viewmodels.NewsFeedViewModelFactory
import com.itheamc.newsfeedappnchl.databinding.FragmentNewsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    // FragmentNewsBinding
    private var _binding: FragmentNewsBinding? = null

    private val binding get() = _binding!!

    /// late init var NewsRecyclerViewAdapter
    lateinit var newsRecyclerViewAdapter: NewsRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initializing FragmentNewsBinding
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        /**
         * Initializing NewsRecyclerViewAdapter and handling onClick callback
         */
        newsRecyclerViewAdapter = NewsRecyclerViewAdapter(
            clickListener = {
                it?.let {

                    val bundle = Bundle()
                    bundle.putString("webUrl", it.webUrl)

                    findNavController().navigate(
                        resId = R.id.action_newsFragment_to_newsDetailsFragment,
                        args = bundle
                    )
                }
            }
        )

        // Setting adapter to the news recycler view
        binding.newsRecyclerView.adapter = newsRecyclerViewAdapter

        /**
         * Getting section id from the arguments that we have passed from NewsSectionFragment
         * with key ---> 'sectionId'
         */
        arguments?.let {
            val sectionId = it.get("sectionId") as String?

            sectionId?.let { id ->

                (activity as AppCompatActivity).supportActionBar?.title = sectionId

                lifecycleScope.launch {
                    viewModel.fetchNews(id)

                    // Listening the data from the flow
                    viewModel.news.collectLatest { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                                binding.progressCircular.visibility = View.VISIBLE
                            }
                            is ApiResult.Success -> {
                                binding.progressCircular.visibility = View.GONE
                                newsRecyclerViewAdapter.submitList(result.data.response.results)
                            }
                            else -> {
                                binding.progressCircular.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}