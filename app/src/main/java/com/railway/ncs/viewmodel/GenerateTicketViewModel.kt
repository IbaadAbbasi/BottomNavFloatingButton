package com.pha.lahore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pha.lahore.repository.GenerateTicketRepository

import com.pha.lahore.state.State
import com.railway.ncs.model.EncryptedRequest
import com.railway.ncs.model.EncryptedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateTicketViewModel @Inject constructor(private val repository: GenerateTicketRepository) : ViewModel() {
    var booleanGenerateTicket = false
    private var _postStateFlowGenerateTicket: MutableStateFlow<State<EncryptedResponse>> =
        MutableStateFlow(State.Empty())
    val postStateFlowGenerateTicket: StateFlow<State<EncryptedResponse>> = _postStateFlowGenerateTicket
    fun generateTicket(EncryptedRequest: EncryptedRequest) = viewModelScope.launch {
        if (booleanGenerateTicket) {
            return@launch
        } else {
            _postStateFlowGenerateTicket.value = State.loading()
            repository.generateTicket(EncryptedRequest)
                .map { response ->
                    State.fromResource(response)
                }.collect { data ->
                    _postStateFlowGenerateTicket.value = data
                }
            booleanGenerateTicket = true
        }
    }

}

