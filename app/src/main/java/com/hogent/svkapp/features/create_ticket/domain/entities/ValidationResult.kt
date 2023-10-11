package com.hogent.svkapp.features.create_ticket.domain.entities

sealed class ValidationResult {
    data object Valid : ValidationResult()
    data class Invalid(val message: String) : ValidationResult()
}
