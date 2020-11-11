package com.example.instafilters

import android.content.Context
import android.graphics.Bitmap
import kotlin.collections.ArrayList

object ThumbnailsManager {
    private var filterThumbs: ArrayList<ThumbnailItem> =
        ArrayList()
    private var processedThumbs: ArrayList<ThumbnailItem> =
        ArrayList()

    fun addThumb(thumbnailItem: ThumbnailItem) {
        filterThumbs.add(thumbnailItem)
    }

    fun processThumbs(context: Context): ArrayList<ThumbnailItem> {
        for (thumb in filterThumbs) {
            // scaling down the image
            val size = context.resources.getDimension(R.dimen.thumbnail_size)
            thumb.image = Bitmap.createScaledBitmap(thumb.image!!, size.toInt(), size.toInt(), false)
            thumb.image = thumb.filter.processFilter(thumb.image)

            processedThumbs.add(thumb)
        }
        return processedThumbs
    }

    fun clearThumbs() {
        filterThumbs.clear()
        processedThumbs.clear()
    }
}