package com.hogent.svkapp.domain.entities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream


/**
 * An error that can occur when validating an [ImageCollection].
 */
enum class ImageCollectionError {
    /**
     * The [ImageCollection] is empty.
     */
    Empty
}

/**
 * A collection of [Image]s.
 *
 * @property value the [Image]s in the collection.
 */
class ImageCollection private constructor(value: List<Image>) {
    private var _value: List<Image> = value
    val value: List<Image> get() = _value

    companion object {
        /**
         * Creates an [ImageCollection].
         *
         * An [ImageCollectionError.Empty] is returned if the [images] are empty.
         *
         * @param images the [Image]s in the collection.
         * @return a [Result] containing either the [ImageCollection] or a [ImageCollectionError].
         */
        fun create(images: List<Image>): Result<ImageCollection, ImageCollectionError> {
            val result = validate(images)

            return if (result != null) {
                Result.Failure(result)
            } else {
                Result.Success(ImageCollection(images))
            }
        }

        /**
         * Validates the [Image]s.
         *
         * @param images the [Image]s to validate.
         *
         * @return an [ImageCollectionError] if the [Image]s are invalid, null otherwise.
         */
        fun validate(images: List<Image>): ImageCollectionError? {
            return if (images.isEmpty()) {
                ImageCollectionError.Empty
            } else {
                null
            }
        }
    }
}

class ImageCollectionConverter() {
    @TypeConverter
    fun fromImageCollection(imageCollection: ImageCollection): String {
        return imageCollection.value.joinToString(";") { (id, bitmap) ->
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val encoded: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
            "$id,$encoded"
        }
    }

    @TypeConverter
    fun toImageCollection(imageCollectionString: String): ImageCollection {
        return (ImageCollection.create(
            imageCollectionString.split(";").map { imageString ->
                val imageStringParts = imageString.split(",")
                val decodedString: ByteArray = Base64.decode(imageStringParts[1], Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                Image(
                    id = imageStringParts[0],
                    bitmap = decodedByte
                )
            }
        ) as Result.Success).value
    }
}
