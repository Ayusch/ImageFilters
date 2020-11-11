package com.example.instafilters

import android.graphics.Bitmap
import com.zomato.photofilters.imageprocessors.Filter

data class ThumbnailItem(var image: Bitmap? = null, var filter: Filter = Filter())
