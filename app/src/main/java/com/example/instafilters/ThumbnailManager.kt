package com.example.instafilters

import android.graphics.Bitmap

object ThumbnailManager {
    var thumbnailItems: ArrayList<ThumbnailItem> = ArrayList()

    fun addThumbnailItem(item: ThumbnailItem) {
        thumbnailItems.add(item)
    }

    fun processThumbnail(): ArrayList<ThumbnailItem> {
        for (thumb in thumbnailItems) {
            thumb.image = thumb.image?.let { Bitmap.createScaledBitmap(it, 80, 80, false) }
            thumb.image = thumb.filter.processFilter(thumb.image)
        }

        return thumbnailItems
    }



    fun clearThumbnails() {
        thumbnailItems.clear()
    }
}