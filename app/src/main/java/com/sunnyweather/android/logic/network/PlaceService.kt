package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *@author kvery
 *@date 2021-09-04 20:27
 */
interface PlaceService {

    @GET("/v2/city/lookup?key=${SunnyWeatherApplication.KEY}")
    fun searchPlace(@Query("location") query: String): Call<PlaceResponse>

}