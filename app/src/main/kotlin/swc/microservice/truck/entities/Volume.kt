package swc.microservice.truck.entities

data class Volume(val value: Double) {

    fun getOccupiedPercentage(capacity: Double): Double = value / capacity
}
