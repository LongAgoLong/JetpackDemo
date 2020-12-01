package com.leo.jetpackdemo.paging.historical

import androidx.paging.Pager
import androidx.paging.PagingConfig

class ArticleRepository {
    fun getHistoricalArticleData() = Pager(PagingConfig(pageSize = 20)) {
        ArticleDataSource()
    }.flow
}