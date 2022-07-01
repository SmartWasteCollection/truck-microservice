package swc.microservice.truck.entities

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon

data class Truck(
    val truckId: String,
    val position: Position = Position(0L, 0L),
    val occupiedVolume: Volume = Volume(0.0),
    val capacity: Double = 100.0,
    val isInMission: Boolean = false
)

fun Truck.toJsonString(): String = Klaxon().toJsonObject(this).put("\$metadata", JsonObject(mapOf(Pair("\$model", this.truckId)))).toString()
