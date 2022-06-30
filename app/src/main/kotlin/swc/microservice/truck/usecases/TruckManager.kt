package swc.microservice.truck.usecases

import com.azure.digitaltwins.core.DigitalTwinsClient
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.identity.AzureCliCredentialBuilder
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.usecases.TruckDigitalTwinManager.Values.EMPTY_TRUCK_MODEL
import swc.microservice.truck.usecases.TruckDigitalTwinManager.Values.MODEL_ID
import java.io.File
import java.nio.file.Files
import java.util.stream.Collectors

fun getAllTrucks(): List<Truck> = TODO()

object TruckDigitalTwinManager {

    object Values {
        const val MODEL_ID = "dtmi:swc:Truck;1"
        const val EMPTY_TRUCK_MODEL = "EmptyTruck.json"
    }

    private val client: DigitalTwinsClient = DigitalTwinsClientBuilder()
        .credential(AzureCliCredentialBuilder().build())
        .endpoint("https://test-instance.api.wcus.digitaltwins.azure.net/")
        .buildClient()

    private val twin: String = this::class.java.classLoader
        .getResource(EMPTY_TRUCK_MODEL)?.readText() ?: EMPTY_TRUCK_MODEL

    fun createTruckDigitalTwin() {

        client.createOrReplaceDigitalTwin("0", twin, String.javaClass)
    }

    fun getTruckDigitalTwinModel(): String = client.getModel(MODEL_ID).dtdlModel

}