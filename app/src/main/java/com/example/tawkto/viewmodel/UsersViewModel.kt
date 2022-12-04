package com.example.tawkto.viewmodel

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*

import com.example.tawkto.data.UserDataSource
import com.example.tawkto.data.repository.UsersRepository
import com.example.tawkto.model.UsersItem
import kotlinx.coroutines.flow.combine
import java.util.concurrent.Flow

 class UsersViewModel(private val usersRepository: UsersRepository) : ViewModel(){
     val query = mutableStateOf("")

     val usersPager = Pager(
        PagingConfig(pageSize = 1)
    ){
        UserDataSource(usersRepository)
    }.flow.cachedIn(viewModelScope)
     fun onQueryChanged(query: String){
         this.query.value = query
     }

     fun searchLocalDatabase(query: String): LiveData<List<UsersItem>>{
         return usersRepository.searchLocalDatabase(query).asLiveData()
     }

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            usersRepository.getUsers()
//        }
//    }
//
//    val users : LiveData<Users>
//    get() = usersRepository.users
}