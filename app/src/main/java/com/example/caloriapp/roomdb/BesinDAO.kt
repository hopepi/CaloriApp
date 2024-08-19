package com.example.caloriapp.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.caloriapp.model.Besin

@Dao
interface BesinDAO {

    @Insert
    suspend fun insertAll(vararg besin: Besin) : List<Long>

    @Query("SELECT * FROM besin")
    suspend fun getAllFood() : List<Besin>

    @Query("SELECT * FROM besin WHERE uuid = :besinID")
    suspend fun getFood(besinID : Int) : Besin

    @Query("DELETE FROM besin")
    suspend fun deleteAllFood()

}