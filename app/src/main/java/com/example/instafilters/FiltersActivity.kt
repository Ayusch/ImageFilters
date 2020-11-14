package com.example.instafilters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class FiltersActivity : AppCompatActivity() {

    lateinit var tvMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)
        tvMessage = findViewById(R.id.tv_message)
        val message = intent.getStringExtra("message") ?: "DesiCoder"
        tvMessage.text = message
    }

}