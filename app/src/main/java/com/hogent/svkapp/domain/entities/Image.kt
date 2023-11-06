package com.hogent.svkapp.domain.entities

import android.graphics.Bitmap
import java.util.UUID

/**
 * An image represented by a [Bitmap].
 *
 * @property id The unique identifier for the image.
 * @property bitmap The [Bitmap] that represents the image.
 */
data class Image(
    val id: String = UUID.randomUUID().toString(), val bitmap: Bitmap
)
