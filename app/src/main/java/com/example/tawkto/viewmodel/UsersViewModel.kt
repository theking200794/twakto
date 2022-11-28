package com.example.tawkto.viewmodel

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import androidx.paging.cachedIn
import com.example.tawkto.data.UserDataSource
import com.example.tawkto.data.repository.UsersRepository
import com.example.tawkto.model.UsersItem
import java.util.concurrent.Flow

 class UsersViewModel(private val usersRepository: UsersRepository) : ViewModel(){
     val usersPager = Pager(
        PagingConfig(pageSize = 1)
    ){
        UserDataSource(usersRepository)
    }.flow.cachedIn(viewModelScope)

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            usersRepository.getUsers()
//        }
//    }
//
//    val users : LiveData<Users>
//    get() = usersRepository.users
}