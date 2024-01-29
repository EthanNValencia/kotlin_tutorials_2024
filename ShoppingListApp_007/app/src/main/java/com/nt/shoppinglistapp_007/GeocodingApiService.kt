package com.nt.shoppinglistapp_007

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitClient {
    private const val BASE_URL = "https://maps.googleapis.com/"

    fun create(): GeocodingApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(GeocodingApiService::class.java)
    }
}
// https://maps.googleapis.com/maps/api/geocode/json?latlng=24,24&key=AIzaSyC-2gFAxzSwQqX41jZ2w1s9CC7f5A5Jf0o
interface GeocodingApiService {
    @GET("maps/api/geocode/json")
    suspend fun getAddressFromCoordinates(
        @Query("latlng") latlng: String,
        @Query("key") apikey: String,
    ): GeocodingResponse
}