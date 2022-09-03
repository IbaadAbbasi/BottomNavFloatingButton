package com.pha.lahore.repository


import com.pha.lahore.network.ApiService
import com.pha.lahore.state.ResponseAPI

import com.railway.ncs.model.EncryptedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CitiesRepository @Inject constructor(private val apiService: ApiService) {



     fun getCities():
             Flow<ResponseAPI<EncryptedResponse>> = flow {
             try {
                 val apiResponse = apiService.allCities()
                 val apiRemoteData = apiResponse.body()
                 if (apiResponse.isSuccessful && apiRemoteData != null) {
                     emit(ResponseAPI.Success(apiRemoteData, apiResponse.code()))
                 } else {
                     emit(ResponseAPI.Failed(apiResponse.message().toString(),apiResponse.code()))
                 }
             } catch (e: Exception) {

                 emit(ResponseAPI.Failed(e.message.toString(),600))
             }


         }.flowOn(Dispatchers.IO)


}

