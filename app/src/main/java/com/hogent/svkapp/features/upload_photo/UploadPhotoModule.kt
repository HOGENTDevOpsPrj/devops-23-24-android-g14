package com.hogent.svkapp.features.upload_photo

import com.hogent.svkapp.features.upload_photo.data.repositories.ImageRepositoryImpl
import com.hogent.svkapp.features.upload_photo.data.repositories.MockImageRepository
import com.hogent.svkapp.features.upload_photo.data.sources.LocalImageDataSource
import com.hogent.svkapp.features.upload_photo.domain.PhotoManager
import com.hogent.svkapp.features.upload_photo.presentation.viewmodel.UploadPhotoViewModel

interface UploadPhotoModule {
    fun getViewModel(): UploadPhotoViewModel
}

class UploadPhotoModuleImpl : UploadPhotoModule {
    private val imageDataSource = LocalImageDataSource()
    private val imageRepository = ImageRepositoryImpl(imageDataSource)
    private val photoManager = PhotoManager(imageRepository)
    private val viewModel = UploadPhotoViewModel(photoManager)

    override fun getViewModel(): UploadPhotoViewModel {
        return viewModel
    }
}

class MockUploadPhotoModule : UploadPhotoModule {
    private val imageRepository = MockImageRepository()
    private val photoManager = PhotoManager(imageRepository)
    private val viewModel = UploadPhotoViewModel(photoManager)

    override fun getViewModel(): UploadPhotoViewModel {
        return viewModel
    }
}