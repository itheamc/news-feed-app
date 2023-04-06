package com.itheamc.newsfeedappnchl.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itheamc.newsfeedappnchl.data.repositories.NewsFeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsFeedViewModel(
    private val newsFeedRepository: NewsFeedRepository
): ViewModel() {

    suspend fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            newsFeedRepository.fetchSections()
                .collect { result ->
                    Log.d("AMIT", ": $result")
                }
        }
    }
}