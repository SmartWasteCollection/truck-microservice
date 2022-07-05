package swc.microservice.truck.drivers

import com.azure.digitaltwins.core.DigitalTwinsClient
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.identity.AzureCliCredentialBuilder
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume
import swc.microservice.truck.adapters.TruckPresentation.Serialization.toJsonString
import swc.microservice.truck.drivers.TruckDigitalTwinManager.Values.DT_QUERY
import swc.microservice.truck.drivers.TruckDigitalTwinManager.Values.ENDPOINT
import swc.microservice.truck.drivers.TruckDigitalTwinManager.Values.MODEL_ID
import swc.microservice.truck.adapters.TruckPresentation.Deserialization.deserialize
import swc.microservice.truck.adapters.TruckPresentation.Deserialization.toJsonObject
import swc.microservice.truck.adapters.TruckPresentation.Serialization.patch
import swc.microservice.truck.usecases.TruckManager

class TruckDigitalTwinManager : TruckManager {

    object Values {
        const val MODEL_ID: String = "dtmi:swc:Truck;1"
        const val ENDPOINT: String = "https://test-instance.api.wcus.digitaltwins.azure.net/"
        const val DT_QUERY: String = "SELECT COUNT() FROM digitaltwins WHERE \$metadata.\$model = 'dtmi:swc:Truck;1'"
    }

    private val client: DigitalTwinsClient = DigitalTwinsClientBuilder()
        .credential(AzureCliCredentialBuilder().build())
        .endpoint(ENDPOINT)
        .buildClient()

    /**
     * Counts how many [Truck] digital twin are deployed.
     */
    override fun getTruckCount(): Int =
        client.query(DT_QUERY, String::class.java)
            .map { it.toString().toJsonObject() }[0]["COUNT"].asInt


    /**
     * Creates a digital twin of the specified [Truck].
     */
    override fun createTruck(truck: Truck): String =
        client.createOrReplaceDigitalTwin(truck.truckId, truck.toJsonString(), String::class.java)

    /**
     * Gets the digital twin of the [Truck] with the specified id.
     */
    override fun getTruck(id: String): Truck =
        deserialize(client.getDigitalTwin(id, String::class.java))

    /**
     * Deletes the digital twin of the [Truck] with the specified id.
     */
    override fun deleteTruck(id: String) {
        client.deleteDigitalTwin(id)
    }

    /**
     * Updates the [Position] of the digital twin with the specified id.
     */
    override fun updateTruckPosition(id: String, position: Position) =
        client.updateDigitalTwin(id, patch(position))

    /**
     * Updates the occupied [Volume] of the digital twin with the specified id.
     */
    override fun updateTruckOccupiedVolume(id: String, volume: Volume) =
        client.updateDigitalTwin(id, patch(volume))

    /**
     * Updates the occupied [Volume] of the digital twin with the specified id.
     */
    override fun updateTruckInMission(id: String, inMission: Boolean) =
        client.updateDigitalTwin(id, patch(inMission))

    /**
     * Gets all the deployed [Truck]s in the digital twin instance.
     */
    override fun getAllTrucks(): List<Truck> {
        TODO("Not yet implemented")
    }

    /**
     * Gets the digital twin model of a [Truck].
     */
    fun getTruckDigitalTwinModel(): String =
        client.getModel(MODEL_ID).dtdlModel

}

