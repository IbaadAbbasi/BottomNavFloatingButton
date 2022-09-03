package com.pha.lahore.network



import com.railway.ncs.model.EncryptedRequest
import com.railway.ncs.model.EncryptedResponse
import retrofit2.Response
import retrofit2.http.*



interface ApiService  {

    @POST("api/app/v1/auth/signin")
    suspend fun login(@Body body: EncryptedRequest): Response<EncryptedResponse>
    @GET("api/v1/city/allCities")
    suspend fun allCities(): Response<EncryptedResponse>


    @POST("api/app/v1/ticket")
    suspend fun generateTicket(@Body body: EncryptedRequest): Response<EncryptedResponse>

    @GET("api/v1/train/")
    suspend fun getAllTrains(): Response<EncryptedResponse>

}

