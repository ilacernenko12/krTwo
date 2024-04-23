package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.NewsDao
import com.example.myapplication.data.RssEntity
import com.example.myapplication.model.RSSObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: NewsDao
): ViewModel() {

    fun insert(rssEntity: RssEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertRssEntity(rssEntity)
        }
    }

    fun getRss(): LiveData<RssEntity?> {
        return dao.getLatestRss()
    }
}