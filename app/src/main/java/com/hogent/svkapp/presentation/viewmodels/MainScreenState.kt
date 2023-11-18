package com.hogent.svkapp.presentation.viewmodels

import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.domain.entities.ImageCollectionError
import com.hogent.svkapp.domain.entities.LicensePlateError
import com.hogent.svkapp.domain.entities.RouteNumberCollectionError
import com.hogent.svkapp.domain.entities.RouteNumberError

data class MainScreenState (
    val routeNumberInputFieldValues : List<String> = listOf(""),
    val licensePlateInputFieldValue : String = "",
    val imageCollection : List<Image> = listOf(),
    val routeNumberInputFieldValidationErrors : List<RouteNumberError?> = listOf(null),
    val routeNumberCollectionError : RouteNumberCollectionError? = null,
    val licensePlateInputFieldValidationError : LicensePlateError? = null,
    val imageCollectionError : ImageCollectionError? = null,
    val showDialog : Boolean = false,
)
