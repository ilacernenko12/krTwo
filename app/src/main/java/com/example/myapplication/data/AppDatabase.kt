package com.example.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.commons.ListConverter
import com.example.myapplication.model.RSSObject

@Database(entities = [RssEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}