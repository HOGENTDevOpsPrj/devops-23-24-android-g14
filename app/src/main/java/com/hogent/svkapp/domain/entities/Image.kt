package com.hogent.svkapp.domain.entities

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import java.util.UUID

sealed class Image private constructor(val id: String) {
    data class BitmapImage(val bitmap: Bitmap) : Image(UUID.randomUUID().toString())
    data class ResourceImage(@DrawableRes val resourceId: Int) : Image(UUID.randomUUID().toString())
}
