package user

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDateTime

class User {

    private var user: JSONObject = JSONObject()

    constructor(user: JSONObject) {
        this.user = user
    }

    fun getCurrentUser(): JSONObject {
        return user
    }

    fun getCreatedData(): String {
        return this.user.get("created_at").toString()
    }

    fun getUsername(): String {
        return this.user.get("user").toString()
    }

    fun getAllPets(): Int {
        try {
            return JSONArray(this.user.get("pets")!!.toString()).length()
        } catch (e: JSONException) {
            return 0
        }
    }

    fun addNewMember(): String {
        println("Registro de Nuevo Miembro")
        println("Nombre: ")
        val name = readLine()!!
        return name
    }

    fun validarRegistro(name: String): Unit {
        val id: Int = getAllPets()
        var allPets = JSONArray()
        val newMember = JSONObject()
        newMember.put("name", name)
        newMember.put("id", id + 1)
        newMember.put("created_at", LocalDateTime.now())
        if (id > 0) {
            allPets = JSONArray(user.get("pets")!!.toString())
        }
        allPets.put(newMember)
        user.put("pets", allPets)
    }

    fun listMembers() {
        val allPets = JSONArray(user.get("pets").toString())
        for ((i, pet) in allPets.withIndex()) {
            println("""${i+1}) ${JSONObject(pet.toString()).get("name")}""")
        }
    }

    fun getInfoPet(position: Int): JSONObject {
        val allPets = JSONArray(user.get("pets").toString())
        return JSONObject(allPets.get(position).toString())
    }
}