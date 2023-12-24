package com.hogent.svkapp.data.repositories

import android.util.Log
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.network.CargoTicketApiService
import com.hogent.svkapp.network.CargoTicketConverter.Companion.convertToApiCargoTicket
import com.hogent.svkapp.util.CustomResult

/**
 * The errors that can occur when adding a [CargoTicket] to the server.
 */
enum class CargoTicketApiAddError {
    /**
     * The [CargoTicket] is invalid.
     */
    INVALID_CARGO_TICKET,

    /**
     * The server encountered an error.
     */
    SERVER_ERROR,

    /**
     * The user is not authenticated.
     */
    AUTHENTICATION_ERROR,

    /**
     * An unknown error occurred.
     */
    UNKNOWN_ERROR
}

/**
 * A repository for [CargoTicket]s. This repository saves the [CargoTicket]s to the server.
 */
interface CargoTicketApiRepository {
    /**
     * Adds a [CargoTicket] to the repository.
     *
     * @param cargoTicket the [CargoTicket] to add.
     * @return a [CustomResult] with the result of the operation. If the operation was successful, the result is [Unit]. If the operation was unsuccessful, the result is a [CargoTicketApiAddError].
     */
    suspend fun addCargoTicket(cargoTicket: CargoTicket): CustomResult<Unit, CargoTicketApiAddError>
}

/**
 * A repository for [CargoTicket]s. This repository sends the [CargoTicket]s to the server.
 *
 * @property cargoTicketApiService the [CargoTicketApiService] that is used to send the [CargoTicket]s to the server.
 */
class CargoTicketApiRepositoryImpl(
    private val cargoTicketApiService: CargoTicketApiService
) : CargoTicketApiRepository {
    override suspend fun addCargoTicket(cargoTicket: CargoTicket): CustomResult<Unit, CargoTicketApiAddError> {
        val apiCargoTicket = cargoTicket.convertToApiCargoTicket()
        val response = cargoTicketApiService.postCargoTicket(apiCargoTicket)

        return if (response.isSuccessful) {
            Log.i("CargoTicketApiRepository", "Cargo ticket added to server")

            CustomResult.Success(Unit)
        } else {
            val message = response.errorBody()?.string() ?: response.message() ?: "Unknown error"

            Log.e(
                "CargoTicketApiRepository", when (response.code()) {
                    401 -> "Authentication error: $message"
                    400 -> "Invalid cargo ticket: $message"
                    500 -> "Server error: $message"
                    else -> "Unknown error: $message"
                }
            )

            when (response.code()) {
                401 -> CustomResult.Failure(CargoTicketApiAddError.AUTHENTICATION_ERROR)
                400 -> CustomResult.Failure(CargoTicketApiAddError.INVALID_CARGO_TICKET)
                500 -> CustomResult.Failure(CargoTicketApiAddError.SERVER_ERROR)
                else -> CustomResult.Failure(CargoTicketApiAddError.UNKNOWN_ERROR)
            }
        }
    }
}