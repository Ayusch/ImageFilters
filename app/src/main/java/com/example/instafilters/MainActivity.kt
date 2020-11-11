package com.example.instafilters

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    var btnClick: Button? = null
    var mImageUri: Uri? = null
    companion object {
        const val REQUEST_CAMERA_CAPTURE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnClick = findViewById(R.id.btn_click)
        btnClick?.setOnClickListener {
            clickImage()
        }
    }

    private fun clickImage() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        mImageUri = ImageUtils.getOutputMediaFileUri(MEDIA_TYPE_IMAGE, this)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val i = Intent(this@MainActivity, FiltersActivity::class.java)
        i.putExtra("imageUri", mImageUri)
        startActivity(i)
    }

}