package swc.microservice.truck.usecases

import com.azure.core.models.JsonPatchDocument
import com.azure.digitaltwins.core.DigitalTwinsClient
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.identity.AzureCliCredentialBuilder
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume
import swc.microservice.truck.usecases.TruckPresentation.Serialization.toJsonString
import swc.microservice.truck.usecases.TruckManager.Values.DT_QUERY
import swc.microservice.truck.usecases.TruckManager.Values.ENDPOINT
import swc.microservice.truck.usecases.TruckManager.Values.MODEL_ID
import swc.microservice.truck.usecases.TruckPresentation.Deserialization.deserialize
import swc.microservice.truck.usecases.TruckPresentation.Serialization.patch

object TruckManager {

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
    fun getTruckCount(): Int = client.query(DT_QUERY, String::class.java).count()

    /**
     * Gets the next available [Truck] digital twin id.
     */
    fun getTruckId(): String = "Truck${getTruckCount()}"

    /**
     * Creates a digital twin of the specified [Truck].
     */
    fun createTruckDigitalTwin(truck: Truck): String =
        client.createOrReplaceDigitalTwin(truck.truckId, truck.toJsonString(), String::class.java)

    /**
     * Gets the digital twin of the [Truck] with the specified id.
     */
    fun readTruckDigitalTwin(id: String): Truck =
        deserialize(client.getDigitalTwin(id, String::class.java))

    /**
     * Deletes the digital twin of the [Truck] with the specified id.
     */
    fun deleteTruckDigitalTwin(id: String) {
        client.deleteDigitalTwin(id)
    }

    /**
     * Updates the [Position] of the digital twin with the specified id.
     */
    fun updateDigitalTwinPosition(id: String, position: Position) =
        client.updateDigitalTwin(id, patch(position))

    /**
     * Updates the occupied [Volume] of the digital twin with the specified id.
     */
    fun updateDigitalTwinOccupiedVolume(id: String, volume: Volume) =
        client.updateDigitalTwin(id, patch(volume))

    /**
     * Updates the occupied [Volume] of the digital twin with the specified id.
     */
    fun updateDigitalTwinInMission(id: String, inMission: Boolean) =
        client.updateDigitalTwin(id, patch(inMission))

    /**
     * Gets the digital twin model of a [Truck].
     */
    fun getTruckDigitalTwinModel(): String =
        client.getModel(MODEL_ID).dtdlModel

}