package com.example.tawkto.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tawkto.data.api.ApiInterface
import com.example.tawkto.model.Users

class UsersRepository(private val apiInterface: ApiInterface) {
    public val usersLiveData = MutableLiveData<Users>()

    val users: LiveData<Users>
        get() = usersLiveData

    suspend fun getUsers(num: Int) {
        val result = apiInterface.getUsers(num)
        if (result.body() != null) {
            usersLiveData.postValue(result.body())
        }
    }
}