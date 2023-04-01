import session.Session
import user.User

fun main() {
    /**
     * Run process to verify exists database
     * If don't exist, it'll create
     * and then create a new account
     */
    val ses = Session()

    /**
     * We'll recover the new account
     * and will create a new user to continue
     * with all available actions
     */
    val user = User(ses.getCurrentUser())

    println("Bienvenido ${user.getUsername()}")
    println("Miembros de la familia: ${user.getAllPets()}")




}