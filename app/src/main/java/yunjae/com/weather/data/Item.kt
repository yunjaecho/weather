package yunjae.com.weather.data

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException

/**
 *
 */
class Item: JSONPopulator {
    private var condition: Condition? = null
    private var forecast: Array<Condition>? = null

    override fun populate(data: JSONObject) {
        condition = Condition()
        condition?.populate(data.optJSONObject("condition"))

        val forecastData: JSONArray = data.optJSONArray("forecast")

        forecast = Array<Condition>(5){Condition()}

        for (i in 0 until forecastData.length()) {
            try {
                forecast!![i].populate(forecastData.getJSONObject(i))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

    }

    override fun toJSON(): JSONObject {
        val data = JSONObject()
        try {
            data.put("condition", condition?.toJSON())
            data.put("forecast", JSONArray(forecast))
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return data
    }
}