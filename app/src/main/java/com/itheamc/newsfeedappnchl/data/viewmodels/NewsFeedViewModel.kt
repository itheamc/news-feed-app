package com.itheamc.newsfeedappnchl.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itheamc.newsfeedappnchl.data.models.ApiResult
import com.itheamc.newsfeedappnchl.data.models.NewsResponseEntity
import com.itheamc.newsfeedappnchl.data.models.SectionResponseEntity
import com.itheamc.newsfeedappnchl.data.repositories.NewsFeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsFeedViewModel(
    private val newsFeedRepository: NewsFeedRepository
) : ViewModel() {

    /**
     * For Sections List
     */
    val sections = MutableStateFlow<ApiResult<SectionResponseEntity>>(ApiResult.Loading)

    /**
     * For News List
     */
    val news = MutableStateFlow<ApiResult<NewsResponseEntity>>(ApiResult.Loading)


    /**
     * Method to fetch sections
     */
    suspend fun fetchSections() {
        viewModelScope.launch(Dispatchers.IO) {
            newsFeedRepository.fetchSections()
                .collect { result ->
                    sections.emit(result)
                }
        }
    }


    /**
     * Method to fetch the news
     */
    suspend fun fetchNews(section: String) {
        viewModelScope.launch(Dispatchers.IO) {
            newsFeedRepository.fetchNews(
                section = section
            )
                .collectLatest { result ->
                    news.emit(result)
                }
        }
    }
}