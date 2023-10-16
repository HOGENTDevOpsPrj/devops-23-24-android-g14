package com.hogent.svkapp.features.upload_image.domain

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import java.util.UUID

data class Image(
    val bitmap: Bitmap? = null,
    @DrawableRes val resourceId: Int? = null,
    val id: String = UUID.randomUUID().toString()
)
