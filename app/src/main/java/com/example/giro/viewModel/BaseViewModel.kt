package com.example.giro.viewModel

import androidx.lifecycle.ViewModel
import com.example.giro.data.network.UserApi
import com.example.giro.data.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel(){

    suspend fun logout(api: UserApi) = repository.logout(api)
}