package com.example.giro.data.repository

import com.example.giro.data.network.UserApi

class UserRepository(
    private val api: UserApi
): BaseRepository() {

    suspend fun getUser() = safeApiCall {
        api.getUser()
    }
}