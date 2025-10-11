class Operator(id: Long,
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

    fun acknowledgeAlert(alert: Alert){}
    fun manageTransformer(transformer: Transformer){}
}