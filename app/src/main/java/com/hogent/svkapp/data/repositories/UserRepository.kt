package com.hogent.svkapp.data.repositories

import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.User
import com.hogent.svkapp.network.ApiUser
import com.hogent.svkapp.network.CargoTicketApiService
import com.hogent.svkapp.network.CargoTicketConverter
import com.hogent.svkapp.network.UserApiService

interface UserRepository {
    suspend fun postUser(user: User): Unit
}

class ApiUserRepository(private val userApiService: UserApiService) : UserRepository {
    /**
     * Adds a [User] to the repository.
     *
     * @param user the [User] to add.
     */
    override suspend fun postUser(user: User): Unit {
        return userApiService.postUser(ApiUser(user.id, user.name))
    }
}