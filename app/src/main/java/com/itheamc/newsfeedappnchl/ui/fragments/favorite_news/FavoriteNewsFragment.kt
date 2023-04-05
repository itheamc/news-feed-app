package com.itheamc.newsfeedappnchl.ui.fragments.favorite_news

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itheamc.newsfeedappnchl.databinding.FragmentFavoriteNewsBinding

class FavoriteNewsFragment : Fragment() {

    // FragmentFavoriteNewsBinding
    private var _binding: FragmentFavoriteNewsBinding? = null

    private val binding get() = _binding!!

    // FavoriteNewsViewModel
    private lateinit var viewModel: FavoriteNewsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initializing FragmentFavoriteNewsBinding
        _binding = FragmentFavoriteNewsBinding.inflate(inflater, container, false)

        // Initializing Favorite News View Model
        viewModel =
            ViewModelProvider(this)[FavoriteNewsViewModel::class.java]

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}