package com.hogent.svkapp.features.upload_photo.data.repositories

import com.hogent.svkapp.features.upload_photo.data.sources.LocalImageDataSource
import com.hogent.svkapp.features.upload_photo.domain.Photo

interface ImageRepository {
    fun loadImages(): List<Photo>

    fun addPhoto(photo: Photo)
}

class ImageRepositoryImpl(private val localImageDataSource: LocalImageDataSource) :
    ImageRepository {
    override fun loadImages(): List<Photo> {
        return localImageDataSource.loadImages()
    }

    override fun addPhoto(photo: Photo) {
        localImageDataSource.addPhoto(photo)
    }
}

class MockImageRepository : ImageRepository {
    override fun loadImages(): List<Photo> {
        return emptyList()
    }

    override fun addPhoto(photo: Photo) {
        // do nothing
    }
}