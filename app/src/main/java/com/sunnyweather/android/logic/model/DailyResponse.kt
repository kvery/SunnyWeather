package com.sunnyweather.android.logic.model

import java.util.*

/**
 *@author kvery
 *@date 2021-09-05 13:05
 */
data class DailyResponse(val code: Int, val daily: List<Daily>) {
    data class Daily(
        val fxDate: Date,
        val tempMax: Float,
        val tempMin: Float,
        val iconDay: String,
        val iconNight: String,
        val precip: Float,
        val humidity: Float
    )
}
