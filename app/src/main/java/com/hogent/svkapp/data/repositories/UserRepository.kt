package com.hogent.svkapp.data.repositories

import com.hogent.svkapp.domain.entities.User
import com.hogent.svkapp.network.ApiUser
import com.hogent.svkapp.network.UserApiService

/**
 * A repository for [User]s.
 */
interface UserRepository {
    /**
     * Adds a [User] to the repository.
     *
     * @param user the [User] to add.
     */
    suspend fun addUser(user: User)
}

/**
 * A repository for [User]s. This repository saves the [User]s to the server.
 */
class UserApiRepository(private val userApiService: UserApiService) : UserRepository {
    override suspend fun addUser(user: User) {
        return userApiService.postUser(ApiUser(user.id, user.name))
    }
}