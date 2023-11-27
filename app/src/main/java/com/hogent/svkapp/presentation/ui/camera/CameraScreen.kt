package com.hogent.svkapp.presentation.ui.camera

import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.hogent.svkapp.presentation.viewmodels.CameraViewModel

@Composable
fun CameraScreen(cameraViewModel: CameraViewModel = viewModel()) {
    val cameraState by cameraViewModel.cameraState.collectAsState()

    CameraContent(
        onPhotoCaptured = cameraViewModel::onSavePhoto,
        lastCapturedPhoto = cameraState.capturedImage
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraContent(
    onPhotoCaptured: (Bitmap) -> Unit,
    lastCapturedPhoto: Bitmap? = null,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProvider = remember {
        ProcessCameraProvider.getInstance(context).get()
    }
    val cameraController = remember {
        LifecycleCameraController(context)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Take Photo") },
                onClick = { },
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Camera capture icon") }
            )
        },
    ) { innerPadding ->
        AndroidView(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    previewView.controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            })
    }

    fun takePhoto(context: Context, cameraProvider: ProcessCameraProvider?, onPhotoCaptured: (Bitmap) -> Unit) {

    }
}