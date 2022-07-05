package swc.microservice.truck.entities

data class Truck(
    val truckId: String,
    val position: Position = Position(0L, 0L),
    val occupiedVolume: Volume = Volume(0.0),
    val capacity: Double = 100.0,
    val isInMission: Boolean = false
)
