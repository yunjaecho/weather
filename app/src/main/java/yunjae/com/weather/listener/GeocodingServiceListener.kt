package yunjae.com.weather.listener

import yunjae.com.weather.data.LocationResut

interface GeocodingServiceListener {

    fun geocodeSuccess(location: LocationResut)

    fun geocodeFailure(exception: Exception)
}