package peek

import session.Session
import user.User
import utils.Utils

class Peek {
    constructor() {
            /**
             * Run process to verify exists database
             * If don't exist, it'll create
             * and then create a new account
             */
            val ses = Session()

            /**
             * We'll recover the new account
             * and will create a new user to continue
             * with all available actions1
             */
            val user = User(ses.getCurrentUser())

            /**
             * We create a new instance to Utils class
             */

            val utils = Utils(ses, user)

            println("Bienvenido ${user.getUsername()}")
            println("Miembros de la familia: ${user.getAllPets()}")

            utils.printMenuUser()

            ses.shutdownApp()
        }
}

fun main() {
    Peek()
}