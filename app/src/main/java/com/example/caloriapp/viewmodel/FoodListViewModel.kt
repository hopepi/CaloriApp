package com.example.caloriapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.caloriapp.model.Besin
import com.example.caloriapp.roomdb.BesinDatabase
import com.example.caloriapp.service.BesinAPIService
import com.example.caloriapp.util.SharedPreferencesObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodListViewModel (application: Application) : AndroidViewModel(application){

    val foods = MutableLiveData<List<Besin>>()
    val foodLoading = MutableLiveData<Boolean>()
    val foodError = MutableLiveData<Boolean>()
    private val updateTime = 10 * 60 * 1000 * 1000 * 1000L

    private val foodApiService = BesinAPIService()
    private val specialSharedPreferences = SharedPreferencesObject(getApplication())

    private fun getInternetData(){
        foodLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val foodList = foodApiService.getFoodDetail()
            withContext(Dispatchers.Main){
                foodLoading.value=false
                saveRoomDatabase(foodList)
            }
        }
    }

    private fun getRoomDatabaseData(){
        foodLoading.value = true
        viewModelScope.launch (Dispatchers.IO){
            val foodList = foodApiService.getFoodDetail()

            withContext(Dispatchers.Main) {
                foodLoading.value = false
                saveRoomDatabase(foodList)
            }
        }
    }

    fun refreshData(){
        val saveTime = specialSharedPreferences.getTime()

        if (saveTime != null && saveTime != 0L && System.nanoTime() - saveTime < updateTime){
            getRoomDatabaseData()
        }else{
            getInternetData()
        }
    }

    fun refreshDataInternet(){
        getInternetData()
    }

    private fun showFoodList(foodList : List<Besin>){
        foods.value = foodList
        foodLoading.value = false
        foodError.value = false
    }


    private fun saveRoomDatabase(foodList : List<Besin>){

        viewModelScope.launch {

            val dao = BesinDatabase.invoke(getApplication()).besinDao()
            dao.deleteAllFood()
            val uuidList = dao.insertAll(*foodList.toTypedArray())//tek tekte ekleyebilirz bunun dezavantajı idleri kendimiz vericez
            var i = 0
            while (i < foodList.size){
                foodList[i].uuid = uuidList[i].toInt()
                i += 1
            }
            showFoodList(foodList)
        }
        specialSharedPreferences.saveTime(System.nanoTime())

    }
}