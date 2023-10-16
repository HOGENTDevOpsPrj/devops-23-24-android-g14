package com.hogent.svkapp.features.upload_photo.domain

import com.hogent.svkapp.features.upload_photo.data.repositories.ImageRepository

class PhotoManager(private val imageRepository: ImageRepository) {

    fun loadImages(): List<Photo> {
        return imageRepository.loadImages()
    }

    fun addPhoto(photo: Photo) {
        imageRepository.addPhoto(photo)
    }
}