package yunjae.com.weather.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weather_condition.*
import yunjae.com.weather.data.Condition
import yunjae.com.weather.data.Units
import android.R.array
import yunjae.com.weather.R


class WeatherConditionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun loadForecast(forecast: Condition, units: Units) {
        val weatherIconImageResource = resources.getIdentifier("icon_" + forecast.getCode(), "drawable", activity.packageName)
        weatherIconImageView.setImageResource(weatherIconImageResource)
        dayTextView.setText(forecast.getDay())

        highTemperatureTextView.setText(getString(R.string.temperature_output, forecast.getHighTemperature(), units.getTemperature()))
    }
}

