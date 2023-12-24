package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.util.CustomResult


/**
 * An error that can occur when validating an [ImageCollection].
 */
enum class ImageCollectionError {
    /**
     * The [ImageCollection] is empty.
     */
    EMPTY
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
         * An [ImageCollectionError.EMPTY] is returned if the [images] are empty.
         *
         * @param images the [Image]s in the collection.
         * @return a [CustomResult] containing either the [ImageCollection] or a [ImageCollectionError].
         */
        fun create(images: List<Image>): CustomResult<ImageCollection, ImageCollectionError> {
            val result = validate(images)

            return if (result != null) {
                CustomResult.Failure(result)
            } else {
                CustomResult.Success(ImageCollection(images))
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
                ImageCollectionError.EMPTY
            } else {
                null
            }
        }
    }
}
