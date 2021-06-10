package com.example.aop_part4_chapter05.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aop_part4_chapter05.Entity.GitHubRepos
import com.example.aop_part4_chapter05.R

class SearchResultRepositoryAdapter(val onClickedItem : (GitHubRepos?) -> Unit) : ListAdapter<GitHubRepos, SearchResultRepositoryAdapter.ViewHolder>(differ) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val userImageView : AppCompatImageView by lazy { itemView.findViewById(R.id.userImageView) }
        private val userLoginTextView : TextView by lazy { itemView.findViewById(R.id.userLoginTextView) }
        private val userUrlTextView : TextView by lazy { itemView.findViewById(R.id.userUrlTextView) }
        private val descTextView : TextView by lazy { itemView.findViewById(R.id.descTextView) }
        private val starCountTextView : TextView by lazy { itemView.findViewById(R.id.starCountTextView) }
        private val languageTextView : TextView by lazy { itemView.findViewById(R.id.languageTextView) }

        fun bind(item : GitHubRepos) {
            Glide.with(userImageView.context)
                .load(item.owner?.avatar_url)
                .circleCrop()
                .into(userImageView)

            userLoginTextView.text = item.owner?.login
            userUrlTextView.text = item.full_name
            descTextView.text = item.description ?: "설명 없음"
            starCountTextView.text = item.stargazers_count.toString()
            languageTextView.text = item.language ?: ""

            itemView.setOnClickListener {
                onClickedItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<GitHubRepos>() {
            override fun areItemsTheSame(oldItem: GitHubRepos, newItem: GitHubRepos): Boolean {
                return oldItem.full_name == newItem.full_name
            }

            override fun areContentsTheSame(oldItem: GitHubRepos, newItem: GitHubRepos): Boolean {
                return oldItem == newItem
            }
        }
    }
}