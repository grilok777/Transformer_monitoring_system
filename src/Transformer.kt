class Transformer (
    id : Long,
    name : String,
    location : String,
    capacity : Double,
    status : TransformerStatus,
    ){
    fun getStatus() : TransformerStatus? {
        return null
    }
    fun updateStatus(){}
    fun addSensorData(sd : SensorData){}
}