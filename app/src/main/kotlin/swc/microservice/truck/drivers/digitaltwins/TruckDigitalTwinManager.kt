package swc.microservice.truck.drivers.digitaltwins

import com.azure.digitaltwins.core.DigitalTwinsClient
import com.azure.digitaltwins.core.DigitalTwinsClientBuilder
import com.azure.identity.AzureCliCredentialBuilder
import swc.microservice.truck.adapters.TruckPresentation.Deserialization.deserialize
import swc.microservice.truck.adapters.TruckPresentation.Deserialization.toJsonObject
import swc.microservice.truck.adapters.TruckPresentation.Serialization.patch
import swc.microservice.truck.adapters.TruckPresentation.Serialization.toJsonString
import swc.microservice.truck.drivers.digitaltwins.DigitalTwinsValues.DIGITAL_TWIN_MODEL
import swc.microservice.truck.drivers.digitaltwins.DigitalTwinsValues.ENDPOINT
import swc.microservice.truck.drivers.digitaltwins.DigitalTwinsValues.TRUCK_MODEL_ID
import swc.microservice.truck.drivers.digitaltwins.QueryBuilder.QueryElements.EQUALS
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume
import swc.microservice.truck.usecases.TruckManager

class TruckDigitalTwinManager : TruckManager {

    private val client: DigitalTwinsClient = DigitalTwinsClientBuilder()
        .credential(AzureCliCredentialBuilder().build())
        .endpoint(ENDPOINT)
        .buildClient()

    /**
     * Creates a digital twin of the specified [Truck].
     */
    override fun createTruck(truck: Truck): String {
        client.createOrReplaceDigitalTwin(truck.truckId, truck.toJsonString(), String::class.java)
        return truck.truckId
    }

    /**
     * Gets the digital twin of the [Truck] with the specified id.
     */
    override fun getTruck(id: String): Truck? = try {
        deserialize(client.getDigitalTwin(id, String::class.java))
    } catch (e: Exception) {
        println(e)
        null
    }

    /**
     * Deletes the digital twin of the [Truck] with the specified id.
     */
    override fun deleteTruck(id: String): Truck? {
        val truck = getTruck(id)
        client.deleteDigitalTwin(id)
        return truck
    }

    /**
     * Updates the [Position] of the digital twin with the specified id.
     */
    override fun updateTruckPosition(id: String, position: Position): Truck? {
        client.updateDigitalTwin(id, patch(position))
        return getTruck(id)
    }

    /**
     * Updates the occupied [Volume] of the digital twin with the specified id.
     */
    override fun updateTruckOccupiedVolume(id: String, volume: Volume): Truck? {
        client.updateDigitalTwin(id, patch(volume))
        return getTruck(id)
    }

    /**
     * Updates the occupied [Volume] of the digital twin with the specified id.
     */
    override fun updateTruckInMission(id: String, inMission: Boolean): Truck? {
        client.updateDigitalTwin(id, patch(inMission))
        return getTruck(id)
    }

    /**
     * Gets all the deployed [Truck]s in the digital twin instance.
     */
    override fun getAllTrucks(): List<Truck> {
        val query = QueryBuilder()
            .selectAll()
            .where(DIGITAL_TWIN_MODEL, EQUALS, "'$TRUCK_MODEL_ID'")
            .build()
        return client.query(query, String::class.java)
            .map { deserialize(it.toJsonObject().toString()) }
    }
}
