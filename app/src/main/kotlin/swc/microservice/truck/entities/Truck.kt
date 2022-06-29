package swc.microservice.truck.entities

data class Truck(
    val truckId: String,
    val position: Position,
    val occupiedVolume: Volume,
    val capacity: Double,
    val isInMission: Boolean = false
)
