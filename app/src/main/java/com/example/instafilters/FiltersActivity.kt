package com.example.instafilters

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.imageprocessors.Filter

class FiltersActivity : AppCompatActivity(), FiltersAdapter.ThumbnailClickListener {

    companion object {
        init {
            System.loadLibrary("NativeImageProcessor")
        }
    }

    lateinit var imageView: ImageView
    lateinit var rvFilters: RecyclerView
    var imageUri: Uri? = null
    var mBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)
        imageView = findViewById(R.id.iv_main)
        rvFilters = findViewById(R.id.rv_filters)

        imageUri = intent.getParcelableExtra<Uri>("imageUri")
        mBitmap = ImageUtils.getBitmap(imageUri!!, contentResolver)
        Glide.with(this).load(mBitmap).into(imageView)

        processRecyclerView()
    }

    fun processRecyclerView() {
        imageUri?.let {
            rvFilters.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val bitmap = ImageUtils.getBitmap(it, contentResolver)

            ThumbnailManager.clearThumbnails()
            val filterPack = FilterPack.getFilterPack(this)
            for (filter in filterPack) {
                val thumbnailItem = ThumbnailItem(bitmap, filter)
                ThumbnailManager.addThumbnailItem(thumbnailItem)
            }

            val processedThumbs = ThumbnailManager.processThumbnail()

            val adapter = FiltersAdapter(processedThumbs, imageUri, this)
            rvFilters.adapter = adapter
        }

    }

    override fun onThumbnailClicked(filter: Filter) {
        mBitmap?.let {
            Glide.with(applicationContext)
                .load(
                    filter.processFilter(
                        Bitmap.createScaledBitmap(
                            it,
                            mBitmap?.width ?: 640,
                            mBitmap?.height ?: 640,
                            false
                        )
                    )
                )
                .into(imageView)
        }

    }
}