package com.hogent.svkapp.features.create_ticket.domain.entities

data class Ticket(
    val routeNumber: Int, val licensePlate: String, val images: List<Image>,
)
