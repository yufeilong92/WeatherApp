package com.example.weatherapp

import android.app.PendingIntent.getActivity
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.weatherapp.weather.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    fun initView() {
        btn_select_weather.setOnClickListener {
            previewDynamicWeather()
        }
        btn_start.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        dynamicWeatherView.onResume()
    }

    override fun onPause() {
        super.onPause()
        dynamicWeatherView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        dynamicWeatherView.onDestroy()
    }

    private fun previewDynamicWeather() {
        val items = arrayOf("晴（白天）", "晴（夜晚）", "多云", "阴", "雨", "雨夹雪", "雪", "冰雹", "雾", "雾霾", "沙尘暴")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("动态天气预览")
        builder.setCancelable(true)
        builder.setSingleChoiceItems(items, -1) { dialog, which ->
            dialog.dismiss()
            switchDynamicWeather(items[which])
        }
        builder.create().show()

    }


    private fun switchDynamicWeather(which: String) {
        val info = ShortWeatherInfo()
        info.code = "100"
        info.windSpeed = "11"
        val type: BaseWeatherType
        when (which) {
            "晴（白天）" -> {
                info.sunrise = "00:01"
                info.sunset = "23:59"
                info.moonrise = "00:00"
                info.moonset = "00:01"
                type = SunnyType(this@MainActivity, info)
            }
            "晴（夜晚）" -> {
                info.sunrise = "00:00"
                info.sunset = "00:01"
                info.moonrise = "00:01"
                info.moonset = "23:59"
                type = SunnyType(this@MainActivity, info)
            }
            "多云" -> {
                info.sunrise = "00:01"
                info.sunset = "23:59"
                info.moonrise = "00:00"
                info.moonset = "00:01"
                val sunnyType = SunnyType(this@MainActivity, info)
                sunnyType.isCloud = true
                type = sunnyType
            }
            "阴" -> type = OvercastType(this@MainActivity, info)
            "雨" -> {
                val rainType = RainType(this@MainActivity, RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2)
                rainType.isFlashing = true
                type = rainType
            }
            "雨夹雪" -> {
                val rainSnowType = RainType(this@MainActivity, RainType.RAIN_LEVEL_1, RainType.WIND_LEVEL_1)
                rainSnowType.isSnowing = true
                type = rainSnowType
            }
            "雪" -> type = SnowType(this@MainActivity, SnowType.SNOW_LEVEL_2)
            "冰雹" -> type = HailType(this@MainActivity)
            "雾" -> type = FogType(this@MainActivity)
            "雾霾" -> type = HazeType(this@MainActivity)
            "沙尘暴" -> type = SandstormType(this@MainActivity)
            else -> type = SunnyType(this@MainActivity, info)
        }
        dynamicWeatherView.setType(type)

    }

//    private fun setDynamicWeatherView(weather: IFakeWeather) {
//        (getParentFragment() as WeatherFragment).getDynamicWeatherView().setOriginWeather(weather)
//        val info = ShortWeatherInfo()
//        info.code = weather.fakeNow.nowCode
//        info.windSpeed = weather.fakeNow.nowWindSpeed
//        info.sunrise = weather.fakeForecastDaily[0].sunRise
//        info.sunset = weather.fakeForecastDaily[0].sunSet
//        info.moonrise = weather.fakeForecastDaily[0].moonRise
//        info.moonset = weather.fakeForecastDaily[0].moonSet
//        if (type == null || System.currentTimeMillis() - type.getLastUpdatedTime() > 30 * 60 * 1000) {
//            type = TypeUtil.getType(getActivity(), info)
//            type.setLastUpdatedTime(System.currentTimeMillis())
//        }
//        dynamicWeatherView.setType(type)
//
//    }
}

