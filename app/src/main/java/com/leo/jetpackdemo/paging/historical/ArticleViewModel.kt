package com.leo.jetpackdemo.paging.historical

import androidx.lifecycle.asLiveData

class ArticleViewModel : BaseViewModel() {

    private val repository: ArticleRepository by lazy { ArticleRepository() }

    /**
     * Pager 分页入口 每个PagingData代表一页数据 最后调用asLiveData将结果转化为一个可监听的LiveData
     */
    fun getHistoricalArticleData() = repository.getHistoricalArticleData().asLiveData()

}