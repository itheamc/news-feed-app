package com.itheamc.newsfeedappnchl.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itheamc.newsfeedappnchl.data.models.Section
import com.itheamc.newsfeedappnchl.databinding.SectionItemViewBinding

class SectionRecyclerViewAdapter(val clickListener: (section: Section?) -> Unit) :
    ListAdapter<Section, SectionRecyclerViewAdapter.MainViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Section>() {
            override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean =
                oldItem.id == newItem.id &&
                        oldItem.sectionId == newItem.sectionId &&
                        oldItem.apiUrl == newItem.apiUrl &&
                        oldItem.webUrl == newItem.webUrl
        }
    }

    inner class MainViewHolder(val binding: SectionItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            SectionItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.apply {
            titleTextView.text = item.webTitle
            urlTextView.text = item.apiUrl
            sectionCard.setOnClickListener { clickListener(item) }
        }
    }
}