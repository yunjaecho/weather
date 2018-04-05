package yunjae.com.weather.service

import android.net.Uri
import android.os.AsyncTask
import yunjae.com.weather.data.Channel
import org.json.JSONObject
import yunjae.com.weather.exception.LocationWeatherException
import yunjae.com.weather.listener.WeatherServiceListener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class YahooWeatherService(_listener: WeatherServiceListener) {
    val listener: WeatherServiceListener = _listener
    var error: Exception? = null
    val temperatureUnit: String = "C"

    fun refreshWeather(location: String) {
        YahooWeatherAsyncTask().execute(location)
    }


    inner class YahooWeatherAsyncTask: AsyncTask<String, Void, Channel>() {
        override fun doInBackground(locations: Array<String>): Channel? {
            val location = locations[0]

            val channel = Channel()

            val unit = if (temperatureUnit.equals("f")) "f" else "c"

            val YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='$unit'", location)

            val endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL))


            try {
                val url = URL(endpoint)

                val connection = url.openConnection()
                connection.setUseCaches(false)

                val inputStream = connection.getInputStream()

                val reader = BufferedReader(InputStreamReader(inputStream))
                val result = StringBuilder()
                var line: String

                reader.lines().forEach {
                    result.append(it)
                }

                reader.close()

                val data = JSONObject(result.toString())

                val queryResults = data.optJSONObject("query")

                val count = queryResults.optInt("count")

                if (count == 0) {
                    error = LocationWeatherException("No weather information found for $location")
                    return null
                }

                val channelJSON = queryResults.optJSONObject("results").optJSONObject("channel")
                channel.populate(channelJSON)

                return channel

            } catch (e: Exception) {
                error = e
            }


            return null
        }

        override fun onPostExecute(channel: Channel) {
            if (channel == null && error != null) {
                listener.serviceFailure(error!!)
            } else {
                listener.serviceSuccess(channel)
            }
        }
    }

}