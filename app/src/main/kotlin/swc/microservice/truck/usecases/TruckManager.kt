package swc.microservice.truck.usecases

import com.azure.digitaltwins.core.DigitalTwinsClient
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.identity.AzureCliCredentialBuilder
import com.beust.klaxon.Json
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.toJsonString
import swc.microservice.truck.usecases.TruckDigitalTwinManager.Values.DT_QUERY
import swc.microservice.truck.usecases.TruckDigitalTwinManager.Values.EMPTY_TRUCK_MODEL
import swc.microservice.truck.usecases.TruckDigitalTwinManager.Values.MODEL_ID
import java.io.File
import java.nio.file.Files
import java.util.stream.Collectors

fun getAllTrucks(): List<Truck> = TODO()

object TruckDigitalTwinManager {

    object Values {
        const val MODEL_ID = "dtmi:swc:Truck;1"
        const val DT_QUERY = "SELECT COUNT() FROM digitaltwins WHERE \$metadata.\$model = 'dtmi:swc:Truck;1'"
    }

    private val client: DigitalTwinsClient = DigitalTwinsClientBuilder()
        .credential(AzureCliCredentialBuilder().build())
        .endpoint("https://test-instance.api.wcus.digitaltwins.azure.net/")
        .buildClient()

    fun getTruckCount(): Int = client.query(DT_QUERY, String::class.java).count()

    fun getTruckId(): String = "Truck${getTruckCount()}"

    fun createTruckDigitalTwin(truck: Truck) {
        client.createOrReplaceDigitalTwin(truck.truckId, truck.toJsonString(), String::class.java)
    }

    fun readTruckDigitalTwin(id: String): Truck? =
        Klaxon().parse<Truck>(client.getDigitalTwin(id, String::class.java))

    fun deleteTruckDigitalTwin(id: String) {
        client.deleteDigitalTwin(id)
    }

    fun updateTruckDigitalTwin(id:String) {
        //client.updateDigitalTwin(id, )
    }

    fun getTruckDigitalTwinModel(): String = client.getModel(MODEL_ID).dtdlModel

}