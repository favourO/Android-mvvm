package com.example.giro.data.network

import com.example.giro.data.responses.User
import okhttp3.ResponseBody
import retrofit2.http.GET

interface UserApi {

    @GET("auth/me")
    suspend fun getUser(): User

    @GET("auth/logout")
    suspend fun logout(): ResponseBody
}