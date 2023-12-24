package com.hogent.svkapp.presentation.ui.qrScanner

import android.content.Context
import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

/**
 * An [ImageAnalysis.Analyzer] that is used to scan barcodes.
 * @param index the index of the text field that is being scanned.
 * @param onRouteNumberChange a callback that is called when the route number changes.
 * @param context the context of the app.
 * @param navigateToForm a callback that is called when the user has scanned a barcode.
 */
@ExperimentalGetImage
class BarcodeAnalyser(
    val index: Int,
    val onRouteNumberChange: (Int, String) -> Unit,
    val context: Context,
    val navigateToForm: () -> Unit,
) : ImageAnalysis.Analyzer {
    /**
     * Analyzes the image that is captured by the camera.
     */
    override fun analyze(imageProxy: ImageProxy) {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()

        val scanner = BarcodeScanning.getClient(options)
        val mediaImage = imageProxy.image
        mediaImage?.let {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.size > 0) {
                        val barcode = barcodes[0].rawValue
                        Log.d("Barcode", "Barcode found: $barcode")
                        onRouteNumberChange(index, barcode ?: "")
                        navigateToForm()
                    }
                }
        }
        imageProxy.close()
    }
}