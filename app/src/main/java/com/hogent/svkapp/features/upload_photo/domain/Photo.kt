package com.hogent.svkapp.features.upload_photo.domain

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import java.util.UUID

data class Photo(
    val bitmap: Bitmap? = null,
    @DrawableRes val resourceId: Int? = null,
    val id: String = UUID.randomUUID().toString()
)
