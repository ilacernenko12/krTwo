package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.FeedAdapter
import com.example.myapplication.commons.RetrofitServiceGenerator
import com.example.myapplication.data.ItemEntity
import com.example.myapplication.data.RssEntity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Feed
import com.example.myapplication.model.Item
import com.example.myapplication.model.RSSObject
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val RSS_link = "https://people.onliner.by/feed"

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = "NEWS by Onliner"
        setSupportActionBar(binding.toolbar)
        val linearLayoutManager = LinearLayoutManager(baseContext,
            LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        loadRSS()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item?.itemId == R.id.menu_refresh) {
            loadRSS()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun loadRSS() {
        // сразу делаем запрос в БД, если там пусто - сходим на сервер
        viewModel.getRss().observe(this) { rssEntity ->
            rssEntity?.let { rssEntity ->
                val adapter = FeedAdapter(convertToRSSObject(rssEntity), this@MainActivity)
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            } ?: run {
                // If RSSObject not in Room, fetch from network
                val call = RetrofitServiceGenerator.createService().getFeed(RSS_link)
                call.enqueue(object : Callback<RSSObject> {
                    override fun onFailure(call: Call<RSSObject>, t: Throwable) {
                        Log.d("ResponseError", "failed")
                    }

                    override fun onResponse(call: Call<RSSObject>, response: Response<RSSObject>) {
                        if (response.isSuccessful) {
                            response.body()?.let { rssObject ->
                                Log.d("Response", "$rssObject")
                                val adapter = FeedAdapter(rssObject, this@MainActivity)
                                binding.recyclerView.adapter = adapter
                                adapter.notifyDataSetChanged()

                                val rssEntity = RssEntity(
                                    status = rssObject.status,
                                    feedUrl = rssObject.feed.url,
                                    feedTitle = rssObject.feed.title,
                                    feedLink = rssObject.feed.link,
                                    feedAuthor = rssObject.feed.author,
                                    feedDescription = rssObject.feed.description,
                                    feedImage = rssObject.feed.image,
                                    items = rssObject.items.map { item ->
                                        ItemEntity(
                                            rssId = 0L, // Temporary, we'll update this
                                            title = item.title,
                                            pubDate = item.pubDate,
                                            link = item.link,
                                            guid = item.guid,
                                            author = item.author,
                                            thumbnail = item.thumbnail,
                                            description = item.description,
                                            content = item.content,
                                            enclosure = item.enclosure.toString(),
                                            categories = item.categories
                                        )
                                    }
                                )
                                // сохранение данных в БД
                                viewModel.insert(rssEntity)
                            }
                        }
                    }
                })
            }
        }
    }

    // маппер
    private fun convertToRSSObject(rssEntity: RssEntity): RSSObject {
        return RSSObject(
            status = rssEntity.status,
            feed = Feed(
                url = rssEntity.feedUrl,
                title = rssEntity.feedTitle,
                link = rssEntity.feedLink,
                author = rssEntity.feedAuthor,
                description = rssEntity.feedDescription,
                image = rssEntity.feedImage
            ),
            items = rssEntity.items.map { itemEntity ->
                Item(
                    title = itemEntity.title,
                    pubDate = itemEntity.pubDate,
                    link = itemEntity.link,
                    guid = itemEntity.guid,
                    author = itemEntity.author,
                    thumbnail = itemEntity.thumbnail,
                    description = itemEntity.description,
                    content = itemEntity.content,
                    enclosure = Object(),
                    categories = itemEntity.categories
                )
            }
        )
    }
}
