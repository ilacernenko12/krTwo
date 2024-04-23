package com.example.myapplication.model

data class RSSObject(
    val status: String,
    val feed: Feed,
    val items: List<Item>
)

