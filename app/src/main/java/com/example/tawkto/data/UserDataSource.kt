package com.example.tawkto.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tawkto.data.repository.UsersRepository
import com.example.tawkto.model.Users
import com.example.tawkto.model.UsersItem
import kotlin.coroutines.coroutineContext

class UserDataSource(private val repo: UsersRepository): PagingSource<Int, UsersItem>() {
    override fun getRefreshKey(state: PagingState<Int, UsersItem>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1)?:page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersItem> {
       return try {
           val page = params.key?: 1

           val response = repo.usersLiveData
           val responseData = mutableListOf<UsersItem>()
           val data = response ?: emptyList<UsersItem>()
           responseData.addAll(data as Collection<UsersItem>)

           LoadResult.Page(
               data = responseData,
               prevKey = null,
               nextKey = repo.usersLiveData.value?.size
           )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}