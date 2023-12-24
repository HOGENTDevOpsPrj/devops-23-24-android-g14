package com.hogent.svkapp.domain.entities

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import com.hogent.svkapp.util.CustomResult

class ImageCollectionTest {

    private lateinit var image: Image

    @Before
    fun setUp() {
        image = mock()
    }

    @Test
    fun imageCollection_Creation_Success() {
        assertTrue(ImageCollection.create(listOf(image)) is CustomResult.Success<ImageCollection, ImageCollectionError>)
    }

    @Test
    fun imageCollection_Creation_Failure() {
        assertTrue(ImageCollection.create(emptyList()) is CustomResult.Failure<ImageCollection, ImageCollectionError>)
    }

    @Test
    fun imageCollection_Validation_Success() {
        assertNull(ImageCollection.validate(listOf(image)))
    }

    @Test
    fun imageCollection_Validation_Failure() {
        assertEquals(ImageCollectionError.EMPTY, ImageCollection.validate(emptyList()))
    }
}