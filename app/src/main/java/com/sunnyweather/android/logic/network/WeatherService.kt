package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.DailyResponse
import com.sunnyweather.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 *@author kvery
 *@date 2021-09-05 13:12
 */
interface WeatherService {
    
    @GET
    fun getRealtimeWeather(
        @Url url: String = "https://devapi.qweather.com/v7/weather/now?key=${SunnyWeatherApplication.KEY}",
        @Query("location") location: String
    ): Call<RealtimeResponse>

    @GET
    fun getDailyWeather(
        @Url url: String = "https://devapi.qweather.com/v7/weather/3d?key=${SunnyWeatherApplication.KEY}",
        @Query("location") location: String
    ): Call<DailyResponse>

}