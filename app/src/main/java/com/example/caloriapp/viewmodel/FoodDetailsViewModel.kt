package com.example.caloriapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.caloriapp.model.Besin
import com.example.caloriapp.roomdb.BesinDatabase
import kotlinx.coroutines.launch


class FoodDetailsViewModel(application: Application) : AndroidViewModel(application) {
    val foodLiveData = MutableLiveData<Besin>()

    fun getRoomData(uuid : Int){
        viewModelScope.launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            val food = dao.getFood(uuid)
            foodLiveData.value = food
        }
    }

}