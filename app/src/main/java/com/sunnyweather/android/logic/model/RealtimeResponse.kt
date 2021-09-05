package com.sunnyweather.android.logic.model

/**
 *@author kvery
 *@date 2021-09-05 12:57
 */
data class RealtimeResponse(val code: Int, val now: Now) {
    data class Now(val temp: Float, val icon: String)
}
