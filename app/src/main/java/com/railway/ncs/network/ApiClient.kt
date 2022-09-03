package com.railway.ncs.network


import com.pha.lahore.network.ApiService
import com.railway.ncs.Utils.WebServiceConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient  {
    val BASE_URL: String = WebServiceConstants.baseUrlRetro


    private val retrofit: Retrofit? = null

    private var httpClient: OkHttpClient.Builder? = null

    fun create(): ApiService {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

/*  val certPinner: CertificatePinner = CertificatePinner.Builder().add(
        "kpkechallangatewayapi.kptportal.com",
        "sha256/FjMlBY1iEERtA8Ita7oQThJkLkgT+j9VmvTRj3VdTa0=").build()*/

        httpClient = OkHttpClient.Builder()
        httpClient!!.readTimeout(60, TimeUnit.SECONDS)
        httpClient!!.connectTimeout(60, TimeUnit.SECONDS)
        // httpClient!!.certificatePinner(certPinner)
        httpClient!!.addInterceptor(logging)



        httpClient!!.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Accept", "application/json")
                .addHeader(
                    "Authorization",
                    "bearer " + "apiServiceImp.updateValueForInterface()"
                )
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        })

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient!!.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)


    }
}
