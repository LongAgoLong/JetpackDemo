package com.leo.jetpackdemo.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.leo.jetpackdemo.R
import com.leo.jetpackdemo.adapter.HistoricalArticleAdapter
import com.leo.jetpackdemo.adapter.GeneralLoadStateAdapter
import com.leo.jetpackdemo.databinding.ActivityPagingBinding
import com.leo.jetpackdemo.paging.historical.ArticleViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

class PagingActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPagingBinding
    private val mAdapter: HistoricalArticleAdapter by lazy { HistoricalArticleAdapter() }
    private val mViewModel by lazy { ViewModelProvider(this).get(ArticleViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar ?: return
        actionBar.setDisplayHomeAsUpEnabled(true)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_paging)
        mBinding.recyclerView.run {
            layoutManager = LinearLayoutManager(
                this@PagingActivity,
                LinearLayoutManager.VERTICAL, false
            )
            adapter = mAdapter.withLoadStateFooter(GeneralLoadStateAdapter(mAdapter))
        }

        /**
         * 获取数据并渲染UI
         * https://www.jianshu.com/p/03054d577d8d
         */
        mViewModel.getHistoricalArticleData().observe(this, Observer {
            lifecycleScope.launchWhenCreated {
                mAdapter.submitData(it)
            }
        })

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            mAdapter.loadStateFlow.collectLatest {
                if (it.refresh !is LoadState.Loading) {
                    /**
                     * 监听刷新状态当刷新完成之后关闭刷新
                     */
                    mBinding.refreshView.isRefreshing = false
                }
            }
        }

        mBinding.refreshView.setOnRefreshListener {
            mAdapter.refresh()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}