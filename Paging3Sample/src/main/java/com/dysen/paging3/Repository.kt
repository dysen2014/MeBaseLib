package com.dysen.paging3

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dysen.paging3.http.res.Res
import kotlinx.coroutines.flow.Flow

/**
 * @author dysen
 * dy.sen@qq.com     4/6/21 3:23 PM
 *
 * Info：
 */
object Repository {

    private const val PAGE_SIZE = 50

    fun getPagingData(): Flow<PagingData<Res.Item>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { RepoPagingSource() }
        ).flow
    }

}

