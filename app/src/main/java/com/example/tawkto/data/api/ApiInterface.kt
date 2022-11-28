package com.example.tawkto.data.api

import com.example.tawkto.model.UsersItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/users")
    suspend fun getUsers(@Query("since")  num: Int): Response<List<UsersItem>>;
}