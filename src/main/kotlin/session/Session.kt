package session

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException
import java.io.File
import java.time.LocalDateTime

class Session {

    private val location: String = "resources/bd/bd.json"
    private var bd: JSONObject = JSONObject()
    private var currentUser: JSONObject= JSONObject()
    constructor() {
        val filePath = "resources/bd/bd.json"
        val file = File(filePath)

        if (isFileExists(file)) {
            println("File exists!!")
        } else {
            println("File doesn't exist or program doesn't have access to it")
            initializeBd()
        }
    }

    private fun initializeBd() {
        try {
            bd.put("users", ArrayList<JSONObject>())
            bd.put("sessionActive", getInitSessionActive())
            registerUser()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun isFileExists(file: File): Boolean {
        return file.exists() && !file.isDirectory
    }

    private fun createFile() {

    }

    private fun getInitSessionActive(): JSONObject {
        val sessionActive = JSONObject()
        sessionActive.put("active", false)
        sessionActive.put("user", "")
        return sessionActive
    }

    private fun registerUser() {
        println("Bienvenido")
        println("Para poder crear su cuenta proporcione un usuario y una contraseña")
        println("Usuario: ")
        var user = readLine()!!
        println("Contraseña: ")
        var pass = readLine()!!
        println("Mantener la sesión activa: (y/n)")
        var stillLogged = readLine()!!
        val response = stillLogged[0].lowercaseChar()
        println("datos proporcionados: <$user> y <$pass>, loggedIn: $response")
        createdNewUser(user, pass, response == 'y')
    }

    private fun createdNewUser(user: String, pass: String, logged: Boolean) {
        var allUsers: JSONArray = bd.getJSONArray("users")
        val newUser = JSONObject()
        newUser.put("user", user)
        newUser.put("pass", pass)
        newUser.put("created_at", LocalDateTime.now())
        println(newUser)
        currentUser = newUser
        allUsers.put(newUser)
        updateUsers(allUsers)
    }

    private fun updateUsers(data: JSONArray) {
        bd.put("users", data)
        println("newUsers $data")
    }

    fun getCurrentUser(): JSONObject {
        return currentUser
    }
}