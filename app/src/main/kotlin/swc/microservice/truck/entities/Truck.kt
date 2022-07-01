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

fun Truck.toJsonString(): String {
    val json = Klaxon().toJsonObject(this)
    json["\$metadata"] = JsonObject(mapOf(Pair("\$model", "dtmi:swc:Truck;1")))
    return json.toJsonString()
}
