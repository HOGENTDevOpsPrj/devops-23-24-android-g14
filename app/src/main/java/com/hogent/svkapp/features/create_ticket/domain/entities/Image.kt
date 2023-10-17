package com.hogent.svkapp.features.create_ticket.domain.entities

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import java.util.UUID

data class Image(
    val bitmap: Bitmap? = null,
    @DrawableRes val resourceId: Int? = null,
    val id: String = UUID.randomUUID().toString()
)
