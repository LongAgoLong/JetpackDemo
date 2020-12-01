package com.leo.jetpackdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leo.jetpackdemo.R
import com.leo.jetpackdemo.databinding.ItemGeneralLoadStateBinding

class GeneralLoadStateAdapter<T : Any, VH : RecyclerView.ViewHolder, X : PagingDataAdapter<T, VH>>(
    private val adapter: X
) : LoadStateAdapter<GeneralLoadStateAdapter.NetworkStateViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateViewHolder {
        return NetworkStateViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_general_load_state,
                parent,
                false
            )
        ) { adapter.retry() }
    }

    class NetworkStateViewHolder(
        private val mBinding: ItemGeneralLoadStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(mBinding.root) {

        init {
            mBinding.retryButton.setOnClickListener {
                retryCallback()
            }
        }

        fun bind(loadState: LoadState) {
            mBinding.progressBar.visibility =
                if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
            mBinding.errorMsg.visibility =
                if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            mBinding.retryButton.visibility =
                if (loadState is LoadState.Error) View.VISIBLE else View.GONE
        }
    }
}