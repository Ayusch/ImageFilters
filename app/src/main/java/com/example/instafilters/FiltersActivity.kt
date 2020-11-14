package com.example.instafilters

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zomato.photofilters.FilterPack

class FiltersActivity : AppCompatActivity() {

    companion object {
        init {
            System.loadLibrary("NativeImageProcessor")
        }
    }

    lateinit var imageView: ImageView
    lateinit var rvFilters: RecyclerView
    var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)
        imageView = findViewById(R.id.iv_main)
        rvFilters = findViewById(R.id.rv_filters)


        imageUri = intent.getParcelableExtra<Uri>("imageUri")
        Glide.with(this).load(imageUri).into(imageView)

        processRecyclerView()
    }

    fun processRecyclerView() {
        imageUri?.let {
            rvFilters.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val bitmap = ImageUtils.getBitmap(it, contentResolver)

            ThumbnailManager.clearThumbnails()
            val filterPack = FilterPack.getFilterPack(this)
            for (filter in filterPack) {
                val thumbnailItem = ThumbnailItem(bitmap, filter)
                ThumbnailManager.addThumbnailItem(thumbnailItem)
            }

            val processedThumbs = ThumbnailManager.processThumbnail()

            val adapter = FiltersAdapter(processedThumbs, imageUri)
            rvFilters.adapter = adapter
        }

    }
}