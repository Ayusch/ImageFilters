package com.example.instafilters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FiltersAdapter() : RecyclerView.Adapter<FiltersAdapter.FiltersViewHolder>() {

    var data = ArrayList<ThumbnailItem>()

    constructor(incomingData: ArrayList<ThumbnailItem>): this() {
        data = incomingData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)
        return FiltersViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {
        holder.textView.text = "Desi Coder"
    }


    class FiltersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_filter)
        val textView: TextView = itemView.findViewById(R.id.tv_filter_name)
    }

}