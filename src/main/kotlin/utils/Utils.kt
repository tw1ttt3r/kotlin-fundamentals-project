package utils

import org.json.JSONArray
import org.json.JSONObject
import session.Session
import user.User

class Utils {

    val menu: JSONArray = JSONArray()
    var menuString: String = """
            Que deseas hacer:
    """
    private val actualSession: Session
    private val actualUser: User

     constructor(session: Session, user: User) {
         actualSession = session
         actualUser = user
         menu.put("Añadir un nuevo miembro de la familia")
         menu.put("Ver a todos los miembros")
         menu.put("Ver el expediente de algún miembro")
         menu.put("Cerrar Sesion")
         menu.put("Salir")
         for ((i, option) in menu.withIndex()) {
             menuString += """${i+1}) $option
                 
             """.trimIndent()
         }
     }

    fun printMenuUser() {
        println(menuString)
        println("Opcion: ")
        getOptionMenu()
    }

    fun getOptionMenu() {
        var option = readLine()!!
        try {
            var menuOption = option.toInt()
            if (menuOption in 1 .. 5) {
                navigateAction(menuOption)
            } else {
                manageActionNoAvaliable(true)
            }
        } catch(e: Exception) {
            manageActionNoAvaliable(false)
        }
    }

    fun manageActionNoAvaliable(type: Boolean) {
        println("Opcion no válida. Proporciona una acción válida")
        printMenuUser()
    }

    fun navigateAction(action: Int) {
        when(action) {
            1 -> addNewMember()
            2 -> listFamily()
            3 -> lookFile()
            4 -> loggedOff()
        }
    }

    fun listFamily() {
        actualUser.listMembers()
        printMenuUser()
    }

    fun addNewMember() {
        println("from addNewMember")
        val dataMember = actualUser.addNewMember()
        if (dataMember == "") {
            println("Datos no proporcionados")
        } else {
            actualUser.validarRegistro(dataMember)
        }
        val newInfoUser = actualUser.getCurrentUser()
        actualSession.updateUserWithExternalInfo(newInfoUser)
        printMenuUser()
    }

    fun lookFile() {
        if (actualUser.getAllPets() > 0) {
            actualUser.listMembers()
            println("Ver expediente de: ")
            val option = readLine()!!
            if (option == "") {
                println("Proporcione una opción válida.")
                lookFile()
            } else {
                val totPets = actualUser.getAllPets()
                if (option.toInt() in 1 .. (totPets + 1)) {
                    val infoPet = actualUser.getInfoPet(option.toInt() - 1)
                    println("Información: $infoPet")
                }
                printMenuUser()
            }
        }
        printMenuUser()
    }

    fun loggedOff() {
        actualSession.loggedOff()
    }
}