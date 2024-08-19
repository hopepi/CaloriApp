package com.example.caloriapp.service

import com.example.caloriapp.model.Besin
import retrofit2.http.GET

interface BesinAPI {

    //Base URL    https://raw.githubusercontent.com/
    //EndPoint    atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json


    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    suspend fun getBesin() : List<Besin>
}