package com.dysen.paging3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dysen.paging3.http.Api
import com.dysen.paging3.http.res.Res


/**
 * @author dysen
 * dy.sen@qq.com     4/6/21 3:17 PM
 *
 * Info：
 */
class RepoPagingSource() : PagingSource<Int, Res.Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Res.Item> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val repoResponse = Api.getProjects(page, pageSize)
            val repoItems = repoResponse.items
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Res.Item>): Int? = null

}

