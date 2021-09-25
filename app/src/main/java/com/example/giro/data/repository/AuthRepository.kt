package com.example.giro.data.repository

import com.example.giro.data.network.AuthApi

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository(){

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

    suspend fun register(
        username : String,
        email: String,
        phone: String,
        role: String,
        password: String
    ) = safeApiCall {
        api.register(username, email, phone, role, password)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }
}