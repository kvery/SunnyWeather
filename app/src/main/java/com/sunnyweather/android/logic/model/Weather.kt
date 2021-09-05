package com.sunnyweather.android.logic.model

/**
 *@author kvery
 *@date 2021-09-05 13:10
 */
data class Weather(val realtime: RealtimeResponse.Now, val dailies: List<DailyResponse.Daily>)
