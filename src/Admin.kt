class Admin(
    id: Long,
    nameUKR: String,
    email: String,
    password: Double,
    role: Role
) : User(id, nameUKR, email, password, role) {

    override fun login() {
        println("Admin nameUKR logged in")
    }

    override fun logout() {
        println("Admin nameUKR logged out")
    }

    override fun viewTransformerStatus(transformer: Transformer) {
        println("Viewing status of transformer: ")
    }

    override fun changePassword() {
        TODO("Not yet implemented")
    }

    override fun changeEmail() {
        TODO("Not yet implemented")
    }

    fun addUser(user: User){}
    fun removeUser(user: User){}
    fun configureSystem(transformer: Transformer){}
}