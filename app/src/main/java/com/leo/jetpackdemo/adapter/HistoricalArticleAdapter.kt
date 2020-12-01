package com.leo.jetpackdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.leo.jetpackdemo.R
import com.leo.jetpackdemo.databinding.ItemHistoricalArticleBinding
import com.leo.jetpackdemo.paging.entity.Article

class HistoricalArticleAdapter :
    PagingDataAdapter<Article, HistoricalArticleAdapter.HistoricalArticleHolder>(POST_COMPARATOR) {

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onBindViewHolder(holder: HistoricalArticleHolder, position: Int) {
        val article = getItem(position) ?: return
        holder.bind(article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalArticleHolder {
        return HistoricalArticleHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_historical_article,
                parent,
                false
            )
        )
    }

    inner class HistoricalArticleHolder(private val mBinding: ItemHistoricalArticleBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(data: Article) {
            mBinding.article = data
        }
    }
}