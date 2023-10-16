package com.hogent.svkapp.features.upload_photo.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.hogent.svkapp.features.upload_photo.domain.Photo
import com.hogent.svkapp.features.upload_photo.domain.PhotoManager

class UploadPhotoViewModel(
    private val photoManager: PhotoManager
) : ViewModel() {
    private var _photos = mutableStateListOf<Photo>()
    val photos: List<Photo> get() = _photos

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        _photos.clear()
        _photos.addAll(photoManager.loadImages())
    }

    fun addPhoto(photo: Photo) {
        photoManager.addPhoto(photo)
        loadPhotos()
    }
}
