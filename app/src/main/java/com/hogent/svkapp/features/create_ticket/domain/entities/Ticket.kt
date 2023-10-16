package com.hogent.svkapp.features.create_ticket.domain.entities

import com.hogent.svkapp.features.upload_image.domain.Image

data class Ticket(
    val routeNumber: Int, val licensePlate: String, val images: List<Image>,
)
