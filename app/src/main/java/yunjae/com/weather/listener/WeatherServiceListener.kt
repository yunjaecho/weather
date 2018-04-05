package yunjae.com.weather.listener

import yunjae.com.weather.data.Channel

/**
 * WeatherServiceListener Interface
 */
interface WeatherServiceListener {
    fun serviceSuccess(channel: Channel)

    fun serviceFailure(exception: Exception)
}