package com.hogent.svkapp.domain.entities

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import java.util.UUID

/**
 * An image.
 *
 * @property id the id of the image.
 */
sealed class Image(val id: String) {
    /**
     * An image that is represented by a [Bitmap].
     *
     * @property bitmap the [Bitmap] that represents the image.
     */
    data class BitmapImage(val bitmap: Bitmap) : Image(UUID.randomUUID().toString())

    /**
     * An image that is represented by a resource id.
     *
     * @property resourceId the resource id that represents the image.
     */
    data class ResourceImage(@DrawableRes val resourceId: Int) : Image(UUID.randomUUID().toString())
}
