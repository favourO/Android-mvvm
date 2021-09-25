package com.example.giro.data.responses

data class UserX(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val isEmailConfirmed: Boolean,
    val phone: String,
    val role: String,
    val status: String,
    val twoFactorEnable: Boolean,
    val username: String
)