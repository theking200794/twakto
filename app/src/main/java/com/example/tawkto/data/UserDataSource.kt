package com.example.tawkto.data

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tawkto.data.repository.UsersRepository
import com.example.tawkto.model.UsersItem
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UserDataSource(private val repo: UsersRepository) : PagingSource<Int, UsersItem>() {
    val users: LiveData<List<UsersItem>>
        get() = repo.users

    override fun getRefreshKey(state: PagingState<Int, UsersItem>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersItem> {
        return try {
            val nextPage = params.key ?: 1
            withContext(IO) {
                repo.getUsers(nextPage)
            }
            LoadResult.Page(
                data = users.value!!,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}