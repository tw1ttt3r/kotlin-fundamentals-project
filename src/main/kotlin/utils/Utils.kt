package utils

import org.json.JSONArray
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
         menuString += menu.reduceIndexed { i, acc, it -> """${if(i > 1) acc else """1) $it""".trimIndent() }
            ${i+1}) $it
        """.trimIndent() }
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
        println("from listFamily")
        println(actualSession.getCurrentUser())
    }

    fun addNewMember() {
        println("from addNewMember")
    }

    fun lookFile() {
        println("from lookFile")
    }

    fun loggedOff() {
        actualSession.loggedOff()
    }
}