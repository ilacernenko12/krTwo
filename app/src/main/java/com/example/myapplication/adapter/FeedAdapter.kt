package com.example.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.OneNewsActivity
import com.example.myapplication.R
import com.example.myapplication.`interface`.ItemClickListener
import com.example.myapplication.model.RSSObject

class FeedAdapter(
    private val rssObject: RSSObject,
    private val mContext: Context
) : RecyclerView.Adapter<FeedViewHolder>() {
    private val inflater = LayoutInflater.from(mContext)

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject.items[position].title
        holder.txtPubDate.text = rssObject.items[position].pubDate

        Glide.with(mContext).load(rssObject.items[position].thumbnail).into(
            holder.srcImage
        )

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                if (!isLongClick) {
                    val intent = Intent(mContext, OneNewsActivity::class.java)
                    intent.putExtra("TITLE", rssObject.items[position].title)
                    intent.putExtra("DATE", rssObject.items[position].pubDate)
                    intent.putExtra("CONTENT", rssObject.items[position].content)
                    intent.putExtra("AUTHOR", rssObject.items[position].author)
                    mContext.startActivity(intent)
                }
            }
        })
    }

    override fun getItemCount(): Int = rssObject.items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.row, parent, false)
        return FeedViewHolder(itemView)
    }
}
