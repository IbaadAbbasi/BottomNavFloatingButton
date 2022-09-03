package com.railway.ncs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.pha.lahore.repository.LoginRepository
import com.pha.lahore.state.State
import com.railway.ncs.model.EncryptedRequest
import com.railway.ncs.model.EncryptedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    var booleanLogin = false
    private var _postStateFlowLogin: MutableStateFlow<State<EncryptedResponse>> =
        MutableStateFlow(State.Empty())
    val postStateFlowLogin: StateFlow<State<EncryptedResponse>> = _postStateFlowLogin
    fun login(loginRequest: EncryptedRequest) = viewModelScope.launch {
        if (booleanLogin) {
            return@launch
        } else {
            _postStateFlowLogin.value = State.loading()
            repository.login(loginRequest)
                .map { response ->
                    State.fromResource(response)
                }.collect { data ->
                    _postStateFlowLogin.value = data
                }
            booleanLogin = true
        }
    }

}

