package com.example.caloriapp.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPreferencesObject {

    companion object{

        private val TIME = "time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile
        private var instance : SharedPreferencesObject? = null

        private val lock  = Any()

        operator fun invoke(context : Context) = instance?: synchronized(lock){
            instance ?: sharedPreferencesOlustur(context).also {
                instance = it
            }

        }

        private fun sharedPreferencesOlustur(context: Context) : SharedPreferencesObject{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesObject()
        }
    }

    fun saveTime(zaman : Long){
        sharedPreferences?.edit()?.putLong(TIME,zaman)?.apply()
    }

    fun getTime() = sharedPreferences?.getLong(TIME,0)


}