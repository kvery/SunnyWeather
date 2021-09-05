package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 *@author kvery
 *@date 2021-09-04 20:16
 */
data class PlaceResponse(val code: Int, @SerializedName("location") val places: List<Place>)

data class Place(val name: String, val lon: String, val lat: String)