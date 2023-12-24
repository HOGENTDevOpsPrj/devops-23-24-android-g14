package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.util.CustomResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock

class ImageCollectionTest {
    @Test
    fun `create ImageCollection with non-empty list should succeed`() {
        val images = listOf(
            Image(
                bitmap = mock()
            )
        )
        val result = ImageCollection.create(images)

        assertTrue(result is CustomResult.Success)
        assertEquals(images, (result as CustomResult.Success).value.value)
    }

    @Test
    fun `create ImageCollection with empty list should fail`() {
        val images = emptyList<Image>()
        val result = ImageCollection.create(images)

        assertTrue(result is CustomResult.Failure)
        assertEquals(ImageCollectionError.EMPTY, (result as CustomResult.Failure).error)
    }

    @Test
    fun `validate with non-empty list should return null`() {
        val images = listOf(Image(bitmap = mock()))
        val result = ImageCollection.validate(images)

        assertNull(result)
    }

    @Test
    fun `validate with empty list should return EMPTY error`() {
        val images = emptyList<Image>()
        val result = ImageCollection.validate(images)

        assertEquals(ImageCollectionError.EMPTY, result)
    }
}
