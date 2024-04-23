package com.example.myapplication.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.`interface`.ItemClickListener
import kotlin.random.Random

open class FeedViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener,
    View.OnLongClickListener {
    var txtTitle: TextView
    var txtPubDate: TextView
    var srcImage: ImageView
    private var itemClickListener: ItemClickListener? = null
    init {
        txtTitle = itemView.findViewById(R.id.textTitle) as TextView
        txtPubDate = itemView.findViewById(R.id.txtPubdate) as TextView
        srcImage = itemView.findViewById(R.id.imageNews) as ImageView
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v, adapterPosition, false)
    }
    override fun onLongClick(v: View?): Boolean {
        val randomColor = Color.argb(255, Random.nextInt(256),
            Random.nextInt(256), Random.nextInt(256))
        itemClickListener!!.onLongClick(v, adapterPosition, randomColor)
        return true
    }
}
