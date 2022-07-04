package swc.microservice.truck.usecases

import com.google.gson.Gson
import com.google.gson.JsonObject
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume

object TruckSerialization {

    private const val METADATA = "\$metadata"
    private const val MODEL = "\$model"
    private const val TRUCK_MODEL = "dtmi:swc:Truck;1"
    private const val TRUCK_ID = "\$dtId"
    private const val POSITION = "position"
    private const val LATITUDE = "latitude"
    private const val LONGITUDE = "longitude"
    private const val OCCUPIED_VOLUME = "occupiedVolume"
    private const val VALUE = "value"
    private const val CAPACITY = "capacity"
    private const val IN_MISSION = "inMission"

    fun Truck.toJsonString(): String = json (
        Pair(METADATA, json(Pair(MODEL, quoted(TRUCK_MODEL)))),
        Pair(POSITION, json(Pair(LATITUDE, this.position.latitude), Pair(LONGITUDE, this.position.longitude))),
        Pair(OCCUPIED_VOLUME, json(Pair(VALUE, this.occupiedVolume.value))),
        Pair(CAPACITY, this.capacity),
        Pair(IN_MISSION, this.isInMission)
    )

    private inline fun <reified T> json(vararg pairs: Pair<String, T>): String = "{ ${
        pairs.map { "\"${it.first}\": ${reify(it.second)}" }
            .reduce { acc, s -> "$acc, $s" }
    } }"

    private inline fun <reified T> reify(elem: T): String = when (elem) {
        is String -> elem
        is Boolean -> when (elem) {
            true -> "true"
            else -> "false"
        }
        else -> elem.toString()
    }

    private fun quoted(s: String): String = "\"$s\""

    fun deserialize(json: String): Truck {
        val obj = json.obj()
        return Truck(
            truckId = obj[TRUCK_ID].asString,
            position = Position(obj[POSITION].obj().get(LATITUDE).asLong, obj[POSITION].obj().get(LONGITUDE).asLong),
            occupiedVolume = Volume(obj[OCCUPIED_VOLUME].obj().get(VALUE).asDouble),
            capacity = obj[CAPACITY].asDouble,
            isInMission = obj[IN_MISSION].asBoolean
        )
    }

    private fun Any.obj(): JsonObject = Gson().fromJson(this.toString(), JsonObject::class.java)
}