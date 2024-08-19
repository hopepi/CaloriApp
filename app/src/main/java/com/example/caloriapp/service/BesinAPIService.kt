package com.example.caloriapp.service

import com.example.caloriapp.model.Besin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BesinAPIService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BesinAPI::class.java)


    suspend fun getFoodDetail() : List<Besin>{
        return retrofit.getBesin()
    }
}