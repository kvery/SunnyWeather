package com.sunnyweather.android.ui.weather

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.sunnyweather.android.R
import com.sunnyweather.android.databinding.ActivityWeatherBinding
import com.sunnyweather.android.databinding.ForecastItemBinding
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    val viewModel: WeatherViewModel by lazy { ViewModelProvider(this).get() }

    lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }

        if (viewModel.locationLon.isEmpty()) {
            viewModel.locationLon = intent.getStringExtra("location_lon") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        viewModel.weatherLiveData.observe(this) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            binding.swipeRefresh.isRefreshing = false
        }
        binding.swipeRefresh.setProgressViewEndTarget(true, 360)
        binding.swipeRefresh.setColorSchemeColors(R.attr.colorPrimary)
        refreshWeather()
        binding.swipeRefresh.setOnRefreshListener { refreshWeather() }

        binding.now.navBtn.setOnClickListener {
            binding.drawLayout.openDrawer(GravityCompat.START)
        }
        binding.drawLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        })
    }

    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLon, viewModel.locationLat)
        binding.swipeRefresh.isRefreshing = true
    }

    private fun showWeatherInfo(weather: Weather) {
        binding.now.placeName.text = viewModel.placeName

        val realtime = weather.realtime
        val dailies = weather.dailies
        // 填充now.xml布局中的数据
        val currentTempText = "${realtime.temp.toInt()} ℃"
        binding.now.currentTemp.text = currentTempText
        binding.now.currentSky.text = getSky(realtime.icon).info
        binding.now.nowLayout.setBackgroundResource(getSky(realtime.icon).bg)

        // val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        // currentAQI.text = currentPM25Text
        // 填充forecast.xml布局中的数据

        binding.forecast.apply {
            forecastLayout.removeAllViews()
            for (day in dailies) {
                val skyCode = day.iconDay
                val tempMax = day.tempMax
                val tempMin = day.tempMin

                val view = LayoutInflater.from(this@WeatherActivity)
                    .inflate(R.layout.forecast_item, binding.forecast.forecastLayout, false)

                ForecastItemBinding.bind(view).apply {
                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    dateInfo.text = simpleDateFormat.format(day.fxDate)
                    val sky = getSky(skyCode)
                    skyIcon.setImageResource(sky.icon)
                    skyInfo.text = sky.info
                    val tempText = "${tempMin.toInt()} ~ ${tempMax.toInt()} ℃"
                    temperatureInfo.text = tempText
                }
                forecastLayout.addView(view)
            }
        }

        binding.lifeIndex.apply {
            // 填充life_index.xml布局中的数据
            val lifeIndex = dailies[0]

            // coldRiskText.text = lifeIndex.coldRisk[0].desc
            // dressingText.text = lifeIndex.dressing[0].desc
            // ultravioletText.text = lifeIndex.ultraviolet[0].desc
            // carWashingText.text = lifeIndex.carWashing[0].desc
        }
        binding.weatherLayout.visibility = View.VISIBLE
    }
}