package com.railway.ncs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pha.lahore.repository.AllTrainsRepository

import com.pha.lahore.repository.LoginRepository
import com.pha.lahore.state.State
import com.railway.ncs.model.EncryptedRequest
import com.railway.ncs.model.EncryptedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAllTrainsViewModel @Inject constructor(private val repository: AllTrainsRepository) : ViewModel() {
    var booleanAllTrains = false
    private var _postStateFlowAllTrain: MutableStateFlow<State<EncryptedResponse>> =
        MutableStateFlow(State.Empty())
    val postStateFlowAllTrains: StateFlow<State<EncryptedResponse>> = _postStateFlowAllTrain
    fun getAllTrains() = viewModelScope.launch {
        if (booleanAllTrains) {
            return@launch
        } else {
            _postStateFlowAllTrain.value = State.loading()
            repository.getAllTrains()
                .map { response ->
                    State.fromResource(response)
                }.collect { data ->
                    _postStateFlowAllTrain.value = data
                }
            booleanAllTrains = true
        }
    }

}

