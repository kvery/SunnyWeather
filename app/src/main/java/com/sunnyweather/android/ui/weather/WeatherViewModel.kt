package com.sunnyweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Location

/**
 *@author kvery
 *@date 2021-09-05 13:50
 */
class WeatherViewModel : ViewModel() {

    private val localLiveData = MutableLiveData<Location>()

    var locationLon = ""
    
    var locationLat = ""

    var placeName = ""

    var weatherLiveData = Transformations.switchMap(localLiveData) { location ->
        Repository.refreshWeather(location.lon, location.lat)
    }

    fun refreshWeather(lon: String, lat: String) {
        localLiveData.value = Location(lon, lat)
    }

}