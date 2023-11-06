package com.hogent.svkapp.domain.entities

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
