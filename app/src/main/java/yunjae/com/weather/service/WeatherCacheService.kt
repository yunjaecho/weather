package yunjae.com.weather.service

import android.content.Context
import android.os.AsyncTask
import yunjae.com.weather.data.Channel
import java.io.FileOutputStream
import java.io.IOException


/**
 * WeatherCacheService
 */
class WeatherCacheService(_context: Context) {
    val context = _context

    lateinit var error: Exception
    val CACHED_WEATHER_FILE: String = "weather.data"

    fun save(channel: Channel) {
        WeatherAsyncTask().execute(channel)
    }

    /**
     *
     */
    inner class WeatherAsyncTask: AsyncTask<Channel, Void, Void>() {
        override fun doInBackground(channels: Array<Channel>): Void? {
            val outputStream: FileOutputStream
            try {
                outputStream = context.openFileOutput(CACHED_WEATHER_FILE, Context.MODE_PRIVATE)
                outputStream.write(channels[0].toJSON().toString().toByteArray())
                outputStream.close()

            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }



}