package user

import org.json.JSONException
import org.json.JSONObject

class User {

    private var user: JSONObject = JSONObject()

    constructor(user: JSONObject) {
        this.user = user
    }

    fun getCreatedData(): String {
        return this.user.get("created_at").toString()
    }

    fun getUsername(): String {
        return this.user.get("user").toString()
    }

    fun getAllPets(): Int {
        try {
            return this.user.get("pets")!!.toString().toInt()
        } catch (e: JSONException) {
            return 0
        }
    }

}