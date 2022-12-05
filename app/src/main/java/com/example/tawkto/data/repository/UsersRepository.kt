package com.example.tawkto.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tawkto.data.api.ApiInterface
import com.example.tawkto.data.db.UserDatabase
import com.example.tawkto.model.UsersItem
import com.example.tawkto.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow

class UsersRepository(
    private val apiInterface: ApiInterface,
    private val userDatabase: UserDatabase,
    private val applicationContext: Context
) {

//    override suspend fun getUsers(num: Int): Response<List<UsersItem>> {
//
//        if (NetworkUtils.isInternetAvailable(applicationContext)){
//            // if internet connection is available
//            var response = apiInterface.getUsers(num)
//            if (response != null){
//                response.body()?.let { userDatabase.userDao().addUsers(it) }
//            }
//            return response
//        }else{
//            // if no internet available local db data will be displayed
//            var userDao = userDatabase.userDao().getUsers()
//            return userDao
//        }
//    }

    private val usersLiveData = MutableLiveData<List<UsersItem>>()

    val users: LiveData<List<UsersItem>>
    get() = usersLiveData

     suspend fun getUsers(num: Int) {

        if (NetworkUtils.isInternetAvailable(applicationContext)){
            // if internet connection is available
            var response = apiInterface.getUsers(num)
            if (response != null){
                response.body()?.let { userDatabase.userDao().addUsers(it) }
                usersLiveData.postValue(response.body())
            }
        }else{
            // if no internet available local db data will be displayed
            var userDao = userDatabase.userDao().getUsers()
            usersLiveData.postValue(userDao)
        }
    }

    fun searchLocalDatabase(query: String): Flow<List<UsersItem>> {
        return userDatabase.userDao().searchLocalDatabase(query)
    }
}