package com.example.giro.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class login(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)