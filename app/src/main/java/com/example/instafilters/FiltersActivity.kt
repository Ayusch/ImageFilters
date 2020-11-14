package com.example.instafilters

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class FiltersActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)
        imageView = findViewById(R.id.iv_main)

        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        Glide.with(this).load(imageUri).into(imageView)
    }

}