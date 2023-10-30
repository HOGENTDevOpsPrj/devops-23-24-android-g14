package com.hogent.svkapp.domain.entities

import android.graphics.Bitmap
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class ImageTest {
    private lateinit var mockBitmap: Bitmap

    @Before
    fun setUp() {
        mockBitmap = mock()
    }

    @Test
    fun `BitmapImage has unique ID`() {
        val image1 = Image.BitmapImage(mockBitmap)
        val image2 = Image.BitmapImage(mockBitmap)

        assertNotEquals(image1.id, image2.id)
    }

    @Test
    fun `ResourceImage has unique ID`() {
        val image1 = Image.ResourceImage(1)
        val image2 = Image.ResourceImage(2)

        assertNotEquals(image1.id, image2.id)
    }

    @Test
    fun `BitmapImage holds correct bitmap`() {
        val image = Image.BitmapImage(mockBitmap)

        assertEquals(mockBitmap, image.bitmap)
    }

    @Test
    fun `ResourceImage holds correct resourceId`() {
        val resourceId = 123
        val image = Image.ResourceImage(resourceId)

        assertEquals(resourceId, image.resourceId)
    }
}
