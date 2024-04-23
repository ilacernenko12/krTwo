package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.myapplication.databinding.ActivityOneNewsBinding
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView

class OneNewsActivity : AppCompatActivity() {
    var title = ""
    var date = ""
    private lateinit var binding: ActivityOneNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOneNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val author = intent.getStringExtra("AUTHOR")
        binding.toolbarOne.title = "Author - \n$author"
        setSupportActionBar(binding.toolbarOne)
        title = intent.getStringExtra("TITLE")!!
        date = intent.getStringExtra("DATE")!!
        val content = intent.getStringExtra("CONTENT")
        binding.oneTextTitle.text = title
        binding.oneTextPubdate.text = date
        val textView = findViewById<View>(R.id.html_text) as HtmlTextView
        textView.setHtml(content!!, HtmlHttpImageGetter(textView))
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.one_page_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.shareBtn) {
            shareData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareData() {
        //concatenate
        val s = title + "\n" + date
        //share intent
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, s)
        startActivity(Intent.createChooser(shareIntent, s))
    }
}
