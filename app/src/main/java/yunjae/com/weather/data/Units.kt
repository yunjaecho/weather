package yunjae.com.weather.data

import org.json.JSONObject

class Units: JSONPopulator {
    private var temperature: String = ""

    fun getTemperature(): String {
        return temperature
    }

    override fun populate(data: JSONObject) {
        temperature = data.optString("temperature")
    }

    override fun toJSON(): JSONObject {
        val data = JSONObject()
        data.put("temperature", temperature)
        return data
    }
}
