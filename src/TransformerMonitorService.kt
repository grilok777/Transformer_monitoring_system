import java.util.logging.LogManager

class TransformerMonitorService(
    users : List<User>,
    transformers: List<Transformer>
) {
    fun addUser(user: User){}
    fun addTransformer(transformer : Transformer){
    }
    fun getTransformerStatus(id : Long) : TransformerStatus?{
        return null
    }
    fun generateReport(){}
}