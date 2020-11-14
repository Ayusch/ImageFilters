package com.example.instafilters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zomato.photofilters.imageprocessors.Filter

class FiltersAdapter(val data: ArrayList<ThumbnailItem>, val imageUri: Uri?, val listener: ThumbnailClickListener) :
    RecyclerView.Adapter<FiltersAdapter.FiltersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)
        return FiltersViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {
        val thumbnailItem = data[position]
        holder.textView.text = thumbnailItem.filter.name
        imageUri?.let {
            Glide.with(holder.imageView.context).load(thumbnailItem.image).into(holder.imageView)
        }
    }


    inner class FiltersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.item_filter)
        val textView: TextView = itemView.findViewById(R.id.tv_filter_name)

        init {
            imageView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onThumbnailClicked(data[adapterPosition].filter)
        }
    }

    interface ThumbnailClickListener {
        fun onThumbnailClicked(filter: Filter)
    }


}