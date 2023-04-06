package com.itheamc.newsfeedappnchl.ui.fragments.news_section

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.itheamc.newsfeedappnchl.NCHLApplication
import com.itheamc.newsfeedappnchl.R
import com.itheamc.newsfeedappnchl.adapters.SectionRecyclerViewAdapter
import com.itheamc.newsfeedappnchl.data.models.ApiResult
import com.itheamc.newsfeedappnchl.data.models.SectionResponseEntity
import com.itheamc.newsfeedappnchl.data.viewmodels.NewsFeedViewModel
import com.itheamc.newsfeedappnchl.data.viewmodels.NewsFeedViewModelFactory
import com.itheamc.newsfeedappnchl.databinding.FragmentNewsSectionBinding
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsSectionFragment : Fragment() {

    // FragmentNewsSectionBinding
    private var _binding: FragmentNewsSectionBinding? = null

    private val binding get() = _binding!!

    /// late init var SectionRecyclerViewAdapter
    lateinit var sectionRecyclerViewAdapter: SectionRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initializing FragmentNewsSectionBinding
        _binding = FragmentNewsSectionBinding.inflate(inflater, container, false)

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
         * Initializing SectionRecyclerViewAdapter and handling onClick callback
         */
        sectionRecyclerViewAdapter = SectionRecyclerViewAdapter(
            clickListener = {
                it?.let {

                    val bundle = Bundle()
                    bundle.putString("sectionId", it.webTitle)

                    findNavController().navigate(
                        resId = R.id.action_navigation_news_section_to_newsFragment,
                        args = bundle
                    )
                }
            }
        )

        // Setting adapter to the section recycler view
        binding.sectionRecyclerView.adapter = sectionRecyclerViewAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                state = Lifecycle.State.CREATED,
                block = {
                    viewModel.fetchSections()
                }
            )

            // Listening the data from the flow
            viewModel.sections.collectLatest { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    is ApiResult.Success<SectionResponseEntity> -> {

                        Log.d("AMIT", "onViewCreated: ${result.data.response.results.size}")

                        binding.progressCircular.visibility = View.GONE
                        sectionRecyclerViewAdapter.submitList(result.data.response.results)
                    }
                    else -> {
                        binding.progressCircular.visibility = View.GONE
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