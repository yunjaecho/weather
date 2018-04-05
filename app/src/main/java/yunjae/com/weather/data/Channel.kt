package yunjae.com.weather.data

import org.json.JSONObject
import org.json.JSONException


class Channel: JSONPopulator {
    private val units: Units = Units()
    private val item: Item = Item()
    private var location: String = ""

    override fun populate(data: JSONObject) {
        units.populate(data.optJSONObject("units"))
        item.populate(data.optJSONObject("item"))

        val locationData = data.optJSONObject("location")

        val region = locationData.optString("region")
        val country = locationData.optString("country")

        location = String.format("%s, %s", locationData.optString("city"), (if (region.length !=0) region else country))

    }

    override fun toJSON(): JSONObject {
        val data = JSONObject()

        try {
            data.put("units", units.toJSON())
            data.put("item", item.toJSON())
            data.put("requestLocation", location)
        } catch (e: JSONException) {
            e.printStackTrace()
        }


        return data
    }
}