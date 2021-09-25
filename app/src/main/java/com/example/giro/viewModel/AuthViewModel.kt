package com.example.giro.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giro.data.network.Resource
import com.example.giro.data.repository.AuthRepository
import com.example.giro.data.responses.LoginRes
import com.example.giro.data.responses.RegRes
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginRes>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginRes>>
        get() = _loginResponse

    private val _registerResponse: MutableLiveData<Resource<RegRes>> = MutableLiveData()
    val registerResponse: LiveData<Resource<RegRes>>
        get() = _registerResponse


    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)
    }

    fun register(
        username: String,
        email: String,
        phone: String,
        role: String,
        password: String
    ) = viewModelScope.launch {
        _registerResponse.value = Resource.Loading
        _registerResponse.value = repository.register(username, email, phone, role, password)
    }

    suspend fun saveAuthToken(token: String) {
        repository.saveAuthToken(token)
    }
}