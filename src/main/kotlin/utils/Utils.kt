package utils

class Utils {

    fun printMenuUser() {
        println("""
            Que deseas hacer:
            1) Añadir un nuevo miembro de la familia
            2) Ver a todos los miembros
            3) Ver el expediente de algún miembro
            4) Cerrar Sesion
            5) Salir
        """.trimIndent())
        println("Opcion: ")
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

    }
}