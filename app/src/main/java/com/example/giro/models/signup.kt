package com.example.giro.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class signup(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("role") val role: String,
    @SerializedName("password") val password: String
){

}