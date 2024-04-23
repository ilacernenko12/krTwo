package com.example.myapplication.commons

import androidx.room.TypeConverter
import com.example.myapplication.data.ItemEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<ItemEntity> {
        val listType = object : TypeToken<List<ItemEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<ItemEntity>): String {
        return gson.toJson(list)
    }
}