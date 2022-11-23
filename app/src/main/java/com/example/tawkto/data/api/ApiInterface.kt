package com.example.tawkto.data.api

import com.example.tawkto.model.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/users")
    suspend fun getUsers(@Query("since")  num: Int): Response<Users>;
}