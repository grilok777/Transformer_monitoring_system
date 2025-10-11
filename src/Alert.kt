import java.time.LocalDateTime

class Alert(
    id : Long,
    transformer: Transformer,
    message : String,
    level : TransformerStatus,
    timestamp : LocalDateTime
) {
    fun send(){}
}