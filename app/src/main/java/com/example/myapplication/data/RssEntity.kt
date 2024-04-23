package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "rss_table")
data class RssEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val status: String,
    val feedUrl: String,
    val feedTitle: String,
    val feedLink: String,
    val feedAuthor: String,
    val feedDescription: String,
    val feedImage: String,
    val items: List<ItemEntity>
)

@Entity(
    tableName = "item_table",
    foreignKeys = [ForeignKey(
        entity = RssEntity::class,
        parentColumns = ["id"],
        childColumns = ["rssId"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val rssId: Long,
    val title: String,
    val pubDate: String,
    val link: String,
    val guid: String,
    val author: String,
    val thumbnail: String,
    val description: String,
    val content: String,
    val enclosure: String,
    val categories: List<String>
)

