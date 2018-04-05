package yunjae.com.weather.data

import org.json.JSONObject

class Condition() : JSONPopulator {
    private var code: Int = 0
    private var temperature: Int = 0
    private var highTemperature: Int = 0
    private var lowTemperature: Int = 0
    private var description: String = ""
    private var day: String = ""

    fun getHighTemperature(): Int {
        return this.highTemperature;
    }

    fun getHighLowTemperature(): Int {
        return this.lowTemperature;
    }



    fun getCode(): Int {
        return code
    }

    fun getDay(): String {
        return day
    }

    override fun populate(data: JSONObject) {
        code = data.optInt("code")
        temperature = data.optInt("temperature")
        highTemperature = data.optInt("highTemperature")
        lowTemperature = data.optInt("lowTemperature")
        description = data.getString("description")
        day = data.getString("day")
    }

    override fun toJSON(): JSONObject {
        val data = JSONObject()

        data.put("code", code)
        data.put("temperature", temperature)
        data.put("highTemperature", highTemperature)
        data.put("lowTemperature", lowTemperature)
        data.put("description", description)
        data.put("day", day)

        return data
    }

}