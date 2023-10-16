package com.hogent.svkapp.features.upload_photo.data.sources

import com.hogent.svkapp.features.upload_photo.domain.Photo

class LocalImageDataSource {
    private val images = mutableListOf<Photo>()

    fun loadImages(): List<Photo> {
        // log photos
        println("Photos loaded: $images")

        return images
    }

    fun addPhoto(photo: Photo) {
        images.add(element = photo)

        // log image
        println("Image added: $photo")
    }
}
