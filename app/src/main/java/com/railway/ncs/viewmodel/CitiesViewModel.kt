package com.pha.lahore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pha.lahore.repository.CitiesRepository

import com.pha.lahore.state.State

import com.railway.ncs.model.EncryptedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val repository: CitiesRepository) : ViewModel() {
    var booleanCities = false
    private var _postStateFlowCities: MutableStateFlow<State<EncryptedResponse>> =
        MutableStateFlow(State.Empty())
    val postStateFlowCities: StateFlow<State<EncryptedResponse>> = _postStateFlowCities
    fun getCities() = viewModelScope.launch {
        if (booleanCities) {
            return@launch
        } else {
            _postStateFlowCities.value = State.loading()
            repository.getCities()
                .map { response ->
                    State.fromResource(response)
                }.collect { data ->
                    _postStateFlowCities.value = data
                }
            booleanCities = true
        }
    }

}

