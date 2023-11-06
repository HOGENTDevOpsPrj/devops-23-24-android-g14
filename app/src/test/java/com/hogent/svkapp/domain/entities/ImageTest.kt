package com.hogent.svkapp.domain.entities

import android.graphics.Bitmap
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.sameInstance
import org.hamcrest.MatcherAssert.assertThat
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

        assertThat(image2.id, not(`is`(image1.id)))
    }

    @Test
    fun `ResourceImage has unique ID`() {
        val image1 = Image.ResourceImage(1)
        val image2 = Image.ResourceImage(2)

        assertThat(image2.id, not(`is`(image1.id)))
    }

    @Test
    fun `BitmapImage holds correct bitmap`() {
        val image = Image.BitmapImage(mockBitmap)

        assertThat(image.bitmap, sameInstance(mockBitmap))
    }

    @Test
    fun `ResourceImage holds correct resourceId`() {
        val resourceId = 123
        val image = Image.ResourceImage(resourceId)

        assertThat(image.resourceId, `is`(resourceId))
    }
}
