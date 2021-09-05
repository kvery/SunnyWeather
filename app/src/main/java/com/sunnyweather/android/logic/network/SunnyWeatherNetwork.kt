package com.sunnyweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *@author kvery
 *@date 2021-09-04 20:45
 */
object SunnyWeatherNetwork {
    private val placeService: PlaceService = ServiceCreator.create()

    private val weatherService: WeatherService = ServiceCreator.create()

    suspend fun searchPlaces(query: String) = placeService.searchPlace(query).await()

    suspend fun getRealtimeWeather(lon: String, lat: String) =
        weatherService.getRealtimeWeather(location = "$lon,$lat").await()

    suspend fun getDailyWeather(lon: String, lat: String) =
        weatherService.getDailyWeather(location = "$lon,$lat").await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}