package swc.microservice.truck

import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import swc.microservice.truck.Values.ERROR_STATUS_CODE
import swc.microservice.truck.Values.MODEL
import swc.microservice.truck.Values.NEW_MISSION
import swc.microservice.truck.Values.NEW_POSITION
import swc.microservice.truck.Values.NEW_VOLUME
import swc.microservice.truck.Values.TRUCK_ID
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume
import swc.microservice.truck.usecases.TruckManager

object Values {
    const val MODEL = "Truck.json"
    const val TRUCK_ID = "TruckTest"
    const val ERROR_STATUS_CODE = 404

    val NEW_POSITION = Position(100L, 100L)
    val NEW_VOLUME = Volume(100.0)
    val NEW_MISSION = true
}

@OptIn(ExperimentalKotest::class)
class TruckManagerTest : FreeSpec({
    concurrency = 1

    "The truck manager" - {
        "when communicating with Azure Digital Twins" - {
            "should fetch a model" {
                val model = this::class.java.classLoader
                    .getResource(MODEL)?.readText()
                    ?.replace("\n", "")
                    ?.replace(" ", "")

                TruckManager.getTruckDigitalTwinModel() shouldBe model
            }
            "should find the right id" {
                val count = TruckManager.getTruckCount()
                TruckManager.getTruckId() shouldBe "Truck${count}"
            }
            "should create a digital twin" {
                val truck = Truck(TRUCK_ID)
                TruckManager.createTruckDigitalTwin(truck)
                TruckManager.readTruckDigitalTwin(truck.truckId) shouldBe truck
            }
            "should update a digital twin" {
                val truck = TruckManager.readTruckDigitalTwin(TRUCK_ID)
                truck.position shouldBe Truck(TRUCK_ID).position
                truck.occupiedVolume shouldBe Truck(TRUCK_ID).occupiedVolume
                truck.isInMission shouldBe Truck(TRUCK_ID).isInMission

                TruckManager.updateDigitalTwinPosition(TRUCK_ID, NEW_POSITION)
                TruckManager.updateDigitalTwinOccupiedVolume(TRUCK_ID, NEW_VOLUME)
                TruckManager.updateDigitalTwinInMission(TRUCK_ID, NEW_MISSION)

                val newTruck = TruckManager.readTruckDigitalTwin(TRUCK_ID)
                newTruck.position shouldBe NEW_POSITION
                newTruck.occupiedVolume shouldBe NEW_VOLUME
                newTruck.isInMission shouldBe NEW_MISSION
            }
            "should delete a digital twin" {
                TruckManager.deleteTruckDigitalTwin(TRUCK_ID)
                val e = shouldThrow<ErrorResponseException> {
                    TruckManager.readTruckDigitalTwin(TRUCK_ID)
                }
                e.response.statusCode shouldBe ERROR_STATUS_CODE
            }
        }
    }
})
