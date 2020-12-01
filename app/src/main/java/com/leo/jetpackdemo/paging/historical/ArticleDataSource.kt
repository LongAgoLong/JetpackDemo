package com.leo.jetpackdemo.paging.historical

import androidx.paging.PagingSource
import com.leo.jetpackdemo.paging.entity.Article
import com.leo.jetpackdemo.paging.entity.HistoricalResult
import com.leo.jetpackdemo.paging.net.ApiProxy
import com.leo.jetpackdemo.paging.net.GsonHelper

class ArticleDataSource : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 0
            val result = ApiProxy.getInstance().getHistoricalData(page)
            val code = result.code()
            val body = result.body()
            val data = GsonHelper.getInstance()
                .fromJson(body, HistoricalResult::class.java)
            if (code == 200 && !body.isNullOrEmpty() && data.errorCode == 0) {
                LoadResult.Page(
                    data = data.data.datas, //需要加载的数据
                    prevKey = null, // 如果可以往上加载更多就设置该参数，否则不设置
                    // 加载下一页的key 如果传null就说明到底了
                    nextKey = if (data.data.curPage == data.data.pageCount) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable("network failed"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}