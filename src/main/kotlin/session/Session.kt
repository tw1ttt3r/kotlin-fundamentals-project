package session

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException
import java.io.File
import java.time.LocalDateTime
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.charset.Charset
import kotlin.system.exitProcess

class Session {

    private val path: String = "src/main/resources/bd.json"
    private var bd: JSONObject = JSONObject()
    private var currentUser: JSONObject= JSONObject()
    constructor() {
        initializeApp()
    }

    private fun initializeApp() {
        val file = File(path)

        if (isFileExists(file)) {
            println("BD running!!")
            loadBD()
        } else {
            println("Creating BD!!...")
            try {
                val isNewFileCreated :Boolean = file.createNewFile()
                if (isNewFileCreated) {
                    println("BD created succesfully!!!")
                    println("BD running!!")
                }
                initializeBd()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadBD(): Unit {
        try {
            val contents = File(path).bufferedReader().readLines()[0]
            bd = JSONObject(contents)
            val allUsers = JSONArray(bd.get("users").toString())
            val sessionActive = JSONObject(bd.get("sessionActive").toString())
            if (sessionActive.get("active").toString().toBoolean()) {
                currentUser = JSONObject(allUsers.filter { JSONObject(it.toString()).get("user") == sessionActive.get("user") }[0].toString())
            } else {
                registerUser(true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
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
        try {
            PrintWriter(FileWriter(path, Charset.defaultCharset()))
                .use { it.write(bd.toString()) }
            println("Muchas gracias por usar nuestro software")
            exitProcess(0);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getInitSessionActive(): JSONObject {
        val sessionActive = JSONObject()
        sessionActive.put("active", false)
        sessionActive.put("user", "")
        return sessionActive
    }

    private fun updateSessionActive(user: String = "", logged: Boolean = false): Unit {
        var sessionLogged: JSONObject = bd.getJSONObject("sessionActive")
        sessionLogged.put("active", logged)
        sessionLogged.put("user", user)
        bd.put("sessionActive", sessionLogged)
    }

    private fun registerUser(login: Boolean = false) {
        println("Bienvenido")
        println(if (!login) "Para poder crear su cuenta proporcione un usuario y una contraseña" else "Para iniciar sesión proporcione un usuario y una contraseña")
        println("Usuario: ")
        var user = readLine()!!
        println("Contraseña: ")
        var pass = readLine()!!
        println("Mantener la sesión activa: (y/n)")
        var stillLogged = readLine()!!
        val response = stillLogged[0].lowercaseChar()
        createdNewUser(user, pass, response == 'y', login)
    }


    private fun createdNewUser(user: String, pass: String, logged: Boolean, login: Boolean) {
        var allUsers: JSONArray = bd.getJSONArray("users")
        var newUser = JSONObject()
        if (!login) {
            newUser.put("user", user)
            newUser.put("pass", pass)
            newUser.put("created_at", LocalDateTime.now())
            allUsers.put(newUser)
            updateUsers(allUsers)
            currentUser = newUser
            updateSessionActive(if(logged) user else "", logged)
        } else {
            val userLogin = allUsers.filter { JSONObject(it.toString()).get("user") == user }
            if (userLogin.isEmpty()) {
                println("Lo sentimos no encontramos ningún usuario con esos datos.")
                println("Quieres Registrarte (y/n)")
                var stillLogged = readLine()!!
                val response = stillLogged[0].lowercaseChar()
                if (response == 'y') {
                    registerUser()
                } else {
                    shutdownApp()
                }
            } else {
                val useer = JSONObject(userLogin[0].toString())
                if (useer.get("pass").toString() == pass) {
                    currentUser = useer
                    updateSessionActive(if(logged) user else "", logged)
                } else {
                    println("Ups! Al parecer tus datos son incorrectos.")
                    shutdownApp()
                }
            }
        }

    }

    private fun updateUsers(data: JSONArray) {
        bd.put("users", data)
    }

    fun getCurrentUser(): JSONObject {
        return currentUser
    }

    fun loggedOff () {
        var sessionLogged: JSONObject = bd.getJSONObject("sessionActive")
        updateSessionActive("", false)
    }

    fun shutdownApp() {
        createFile()
    }
}