package com.itheamc.newsfeedappnchl.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itheamc.newsfeedappnchl.data.repositories.NewsFeedRepository

class NewsFeedViewModelFactory(
    private val newsFeedRepository: NewsFeedRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsFeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsFeedViewModel(newsFeedRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}