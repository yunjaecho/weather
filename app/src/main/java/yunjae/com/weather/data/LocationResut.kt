package yunjae.com.weather.data

import org.json.JSONObject

class LocationResut: JSONPopulator {
    private var address: String = ""

    override fun populate(data: JSONObject) {
        address = data.optString("formatted_address")
    }

    override fun toJSON(): JSONObject {
        val data = JSONObject()
        data.put("formatted_address", address)
        return data
    }

}
