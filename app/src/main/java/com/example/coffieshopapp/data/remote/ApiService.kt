package com.example.coffieshopapp.data.remote

import com.example.coffieshopapp.data.Model.CoffeeResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("getCoffie.php")
    suspend fun getCoffees(): CoffeeResponse
}

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.4/coffieApi/" // 10.0.2.2 is localhost for Android Emulator

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
