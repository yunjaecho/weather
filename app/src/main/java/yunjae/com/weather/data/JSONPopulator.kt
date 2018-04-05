package yunjae.com.weather.data

import org.json.JSONObject

interface JSONPopulator {
    fun populate(data: JSONObject)

    fun toJSON(): JSONObject
}