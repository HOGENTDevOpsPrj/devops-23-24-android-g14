package com.hogent.svkapp.features.create_ticket.domain.entities

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val message: String) : ValidationResult()
}
