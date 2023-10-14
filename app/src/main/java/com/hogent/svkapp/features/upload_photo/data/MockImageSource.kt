import com.hogent.svkapp.R
import com.hogent.svkapp.features.upload_photo.domain.ImageResource

class MockImageSource() {
    fun loadImages(): List<ImageResource> {
        return listOf<ImageResource>(
            ImageResource(R.drawable.resource_default),
            ImageResource(R.drawable.resource_default),
            ImageResource(R.drawable.resource_default),
            ImageResource(R.drawable.resource_default),
            ImageResource(R.drawable.resource_default),
            ImageResource(R.drawable.resource_default),
            ImageResource(R.drawable.resource_default),
            ImageResource(R.drawable.resource_default),
            ImageResource(R.drawable.resource_default),
            ImageResource(R.drawable.resource_default))
    }
}
