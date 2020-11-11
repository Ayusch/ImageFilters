package com.example.instafilters

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.imageprocessors.Filter
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream


class ThumbnailAdapter(private val activity: Activity) :
    RecyclerView.Adapter<ThumbnailAdapter.ThumbnailViewHolder>() {


    var data = ArrayList<ThumbnailItem>()
    lateinit var mClickListener: ThumbnailClickListener

    constructor(activity: Activity, clickListener: ThumbnailClickListener, thumbs: ArrayList<ThumbnailItem>) : this(
        activity
    ) {
        data = thumbs
        mClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.thumbnail_item, parent, false)
        return ThumbnailViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        Glide.with(holder.imageView.context).load(data[position].image).into(holder.imageView)
        holder.textView.text = data[position].filter.name
    }

    inner class ThumbnailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var imageView: ImageView = itemView.findViewById(R.id.iv_thumb)
        var textView: TextView = itemView.findViewById(R.id.tv_filter_name)

        init {
            imageView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            mClickListener.onClickThumbnail(data[adapterPosition].filter)
        }
    }

    interface ThumbnailClickListener {
        fun onClickThumbnail(filter: Filter)
    }

}