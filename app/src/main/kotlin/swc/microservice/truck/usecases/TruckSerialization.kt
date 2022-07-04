package swc.microservice.truck.usecases

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume
import swc.microservice.truck.usecases.TruckSerialization.Values.METADATA
import swc.microservice.truck.usecases.TruckSerialization.Values.MODEL
import swc.microservice.truck.usecases.TruckSerialization.Values.TRUCK_MODEL
import swc.microservice.truck.usecases.TruckSerialization.Values.TRUCK_ID
import swc.microservice.truck.usecases.TruckSerialization.Values.POSITION
import swc.microservice.truck.usecases.TruckSerialization.Values.LATITUDE
import swc.microservice.truck.usecases.TruckSerialization.Values.LONGITUDE
import swc.microservice.truck.usecases.TruckSerialization.Values.OCCUPIED_VOLUME
import swc.microservice.truck.usecases.TruckSerialization.Values.VALUE
import swc.microservice.truck.usecases.TruckSerialization.Values.CAPACITY
import swc.microservice.truck.usecases.TruckSerialization.Values.IN_MISSION

object TruckSerialization {

    private object Values {

        const val METADATA = "\$metadata"
        const val MODEL = "\$model"
        const val TRUCK_MODEL = "dtmi:swc:Truck;1"
        const val TRUCK_ID = "\$dtId"
        const val POSITION = "position"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val OCCUPIED_VOLUME = "occupiedVolume"
        const val VALUE = "value"
        const val CAPACITY = "capacity"
        const val IN_MISSION = "inMission"
    }

    /**
     * Converts a [Truck] into a Json String.
     */
    fun Truck.toJsonString(): String = json (
        Pair(METADATA, json(Pair(MODEL, quoted(TRUCK_MODEL)))),
        Pair(POSITION, json(Pair(LATITUDE, this.position.latitude), Pair(LONGITUDE, this.position.longitude))),
        Pair(OCCUPIED_VOLUME, json(Pair(VALUE, this.occupiedVolume.value))),
        Pair(CAPACITY, this.capacity),
        Pair(IN_MISSION, this.isInMission)
    )

    /**
     * Allows to convert a map of elements (represented by [Pair]s) into a Json String.
     */
    private fun <T> json(vararg pairs: Pair<String, T>): String = "{ ${
        pairs.map { "\"${it.first}\": ${valueOf(it.second)}" }
            .reduce { acc, s -> "$acc, $s" }
    } }"

    /**
     * Converts a generic element into its corresponding Json representation value.
     */
    private fun <T> valueOf(elem: T): String = when (elem) {
        is String -> elem
        is Boolean -> when (elem) {
            true -> "true"
            else -> "false"
        }
        else -> elem.toString()
    }

    /**
     * Quotes a string.
     */
    private fun quoted(s: String): String = "\"$s\""

    /**
     * Deserializes a Json into a Truck object.
     */
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

    /**
     * Converts [Any]thing into a [JsonObject]. Throws a [JsonSyntaxException] if the parsed object is not a valid json.
     */
    @kotlin.jvm.Throws(JsonSyntaxException::class)
    private fun Any.obj(): JsonObject =
        Gson().fromJson(this.toString(), JsonObject::class.java)
}