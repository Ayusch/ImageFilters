package com.example.instafilters

import android.graphics.Bitmap
import com.zomato.photofilters.imageprocessors.Filter

data class ThumbnailItem (var image: Bitmap?, val filter: Filter)