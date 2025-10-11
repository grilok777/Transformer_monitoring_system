
abstract class User (
    id : Long,
    nameUKR : String,
    email : String,
    password : Double,
    role : Role,
    ){
    abstract fun login()
    abstract fun logout()
    abstract fun viewTransformerStatus(transformer: Transformer)
    abstract fun changePassword()
    abstract fun changeEmail()
}