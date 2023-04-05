package com.itheamc.newsfeedappnchl.ui.fragments.news_section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.itheamc.newsfeedappnchl.databinding.FragmentNewsSectionBinding

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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}