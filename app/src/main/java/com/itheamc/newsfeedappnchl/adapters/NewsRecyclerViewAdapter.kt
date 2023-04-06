package com.itheamc.newsfeedappnchl.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itheamc.newsfeedappnchl.data.models.News
import com.itheamc.newsfeedappnchl.databinding.NewsItemViewBinding

class NewsRecyclerViewAdapter(val clickListener: (section: News?) -> Unit) :
    ListAdapter<News, NewsRecyclerViewAdapter.MainViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem.id == newItem.id &&
                        oldItem.sectionId == newItem.sectionId &&
                        oldItem.apiUrl == newItem.apiUrl &&
                        oldItem.webUrl == newItem.webUrl
        }
    }

    inner class MainViewHolder(val binding: NewsItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            NewsItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.apply {
            titleTextView.text = item.webTitle
            urlTextView.text = item.webPublicationDate
            newsCard.setOnClickListener { clickListener(item) }
        }
    }
}