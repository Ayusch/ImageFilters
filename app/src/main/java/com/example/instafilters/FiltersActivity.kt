package com.example.instafilters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.imageprocessors.Filter
import kotlin.concurrent.thread

class FiltersActivity : AppCompatActivity(), ThumbnailAdapter.ThumbnailClickListener {
    companion object {
        init {
            System.loadLibrary("NativeImageProcessor");
        }
    }

    private lateinit var ivOriginalImage: ImageView
    private var mImageUri: Uri? = null
    private lateinit var rvThumbnail: RecyclerView
    private var mBitmap: Bitmap? = null
    private lateinit var btnSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)
        ivOriginalImage = findViewById(R.id.iv_original_image)
        rvThumbnail = findViewById(R.id.rv_thumbnail)
        btnSend = findViewById(R.id.btn_send)

        mImageUri = intent?.getParcelableExtra("imageUri")
        mBitmap = ImageUtils.getBitmap(mImageUri!!, contentResolver)
        Glide.with(ivOriginalImage.context ?: applicationContext).load(mBitmap)
            .into(ivOriginalImage)

        init()
    }

    private fun init() {
        rvThumbnail.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        processThumbnails()
        setSendClickListener()
    }

    private fun processThumbnails() {
        val context: Context = this.application
        val thumbImage = Bitmap.createScaledBitmap(
            mBitmap!!, 640, 640, false
        )
        ThumbnailsManager.clearThumbs()
        val filters =
            FilterPack.getFilterPack(applicationContext)
        for (filter in filters) {
            val thumbnailItem = ThumbnailItem()
            thumbnailItem.image = thumbImage
            thumbnailItem.filter = filter!!
            ThumbnailsManager.addThumb(thumbnailItem)
        }
        val thumbs =
            ThumbnailsManager.processThumbs(context)
        val adapter =
            ThumbnailAdapter(this, this, thumbs)
        rvThumbnail.adapter = adapter
    }

    override fun onClickThumbnail(filter: Filter) {
        val bitmapCopy = mBitmap!!.copy(Bitmap.Config.ARGB_8888, true)
        Glide.with(this).load(
            Bitmap.createScaledBitmap(
                filter.processFilter(bitmapCopy),
                mBitmap?.width ?: 640,
                mBitmap?.height ?: 640,
                false
            )
        ).into(ivOriginalImage)
        bitmapCopy.recycle()
    }

    private fun setSendClickListener() {
        btnSend.setOnClickListener {
            mImageUri?.let {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "image/png"
                share.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                share.putExtra(Intent.EXTRA_STREAM, it)
                startActivity(share)
            }
        }
    }

}