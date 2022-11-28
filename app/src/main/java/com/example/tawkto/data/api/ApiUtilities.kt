package com.example.tawkto.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitInst {
   companion object {
       val BASE_URL = "https://api.github.com/"
       private val retrofit by lazy {
           Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
       }

        val apiUsers = retrofit.create(ApiInterface::class.java)
    }
}