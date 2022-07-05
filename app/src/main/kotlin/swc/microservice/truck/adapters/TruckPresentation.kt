package swc.microservice.truck.adapters

import com.azure.core.models.JsonPatchDocument
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import swc.microservice.truck.adapters.TruckPresentation.Values.CAPACITY
import swc.microservice.truck.adapters.TruckPresentation.Values.IN_MISSION
import swc.microservice.truck.adapters.TruckPresentation.Values.LATITUDE
import swc.microservice.truck.adapters.TruckPresentation.Values.LONGITUDE
import swc.microservice.truck.adapters.TruckPresentation.Values.METADATA
import swc.microservice.truck.adapters.TruckPresentation.Values.MODEL
import swc.microservice.truck.adapters.TruckPresentation.Values.OCCUPIED_VOLUME
import swc.microservice.truck.adapters.TruckPresentation.Values.POSITION
import swc.microservice.truck.adapters.TruckPresentation.Values.TRUCK_ID
import swc.microservice.truck.adapters.TruckPresentation.Values.TRUCK_MODEL
import swc.microservice.truck.adapters.TruckPresentation.Values.VALUE
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume

object TruckPresentation {

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

    object Serialization {

        /**
         * Produces a [JsonPatchDocument] that updates a serialized [Truck].
         */
        fun <T> patch(elem: T): JsonPatchDocument = when (elem) {
            is Position -> JsonPatchDocument().appendReplace("/$POSITION", elem)
            is Volume -> JsonPatchDocument().appendReplace("/$OCCUPIED_VOLUME", elem)
            is Boolean -> JsonPatchDocument().appendReplace("/$IN_MISSION", elem)
            else -> JsonPatchDocument()
        }

        /**
         * Converts a [Truck] into a Json String.
         */
        fun Truck.toJsonString(): String = json(
            Pair(METADATA, json(Pair(MODEL, "\"$TRUCK_MODEL\""))),
            Pair(POSITION, json(Pair(LATITUDE, this.position.latitude), Pair(LONGITUDE, this.position.longitude))),
            Pair(OCCUPIED_VOLUME, json(Pair(VALUE, this.occupiedVolume.value))),
            Pair(CAPACITY, this.capacity),
            Pair(IN_MISSION, this.isInMission)
        )

        /**
         * Allows to convert a map of elements (represented by [Pair]s) into a Json String.
         */
        private fun <T> json(vararg pairs: Pair<String, T>): String = "{ ${
        pairs.map { "\"${it.first}\": ${it.second}" }
            .reduce { acc, s -> "$acc, $s" }
        } }"
    }

    object Deserialization {

        /**
         * Deserializes a Json into a Truck object.
         */
        fun deserialize(json: String): Truck {
            val obj = json.toJsonObject()
            return Truck(
                truckId = obj[TRUCK_ID].asString,
                position = Position(
                    obj[POSITION].toJsonObject().get(LATITUDE).asLong,
                    obj[POSITION].toJsonObject().get(LONGITUDE).asLong
                ),
                occupiedVolume = Volume(obj[OCCUPIED_VOLUME].toJsonObject().get(VALUE).asDouble),
                capacity = obj[CAPACITY].asDouble,
                isInMission = obj[IN_MISSION].asBoolean
            )
        }

        /**
         * Converts [Any]thing into a [JsonObject]. Throws a [JsonSyntaxException] if the parsed object is not a valid json.
         */
        @kotlin.jvm.Throws(JsonSyntaxException::class)
        fun Any.toJsonObject(): JsonObject =
            Gson().fromJson(this.toString(), JsonObject::class.java)
    }
}
