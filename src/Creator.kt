class Creator(id: Long,
              nameUKR: String,
              email: String,
              password: Double,
              role: Role
) : User(id, nameUKR, email, password, role) {
    override fun login() {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun viewTransformerStatus(transformer: Transformer) {
        TODO("Not yet implemented")
    }

    override fun changePassword() {
        TODO("Not yet implemented")
    }

    override fun changeEmail() {
        TODO("Not yet implemented")
    }

    fun createOperator(){}
    fun createAdmin(){}
    fun createDataAnalyst(){}
    fun setRoleOfUser(user: User){}
    fun deleteUser(user: User){}
    fun findUser(user: User) : User?{
        return null
    }
}