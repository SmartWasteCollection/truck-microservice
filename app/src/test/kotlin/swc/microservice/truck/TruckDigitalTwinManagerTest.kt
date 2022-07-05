package swc.microservice.truck

import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import swc.microservice.truck.Values.ERROR_STATUS_CODE
import swc.microservice.truck.Values.MODEL
import swc.microservice.truck.Values.NEW_MISSION
import swc.microservice.truck.Values.NEW_POSITION
import swc.microservice.truck.Values.NEW_VOLUME
import swc.microservice.truck.Values.TRUCK_ID
import swc.microservice.truck.Values.manager
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume
import swc.microservice.truck.drivers.TruckDigitalTwinManager

object Values {
    const val MODEL = "Truck.json"
    const val TRUCK_ID = "TruckTest"
    const val ERROR_STATUS_CODE = 404

    val NEW_POSITION = Position(100L, 100L)
    val NEW_VOLUME = Volume(100.0)
    val NEW_MISSION = true

    val manager: TruckDigitalTwinManager = TruckDigitalTwinManager()
}

class TruckManagerTest : FreeSpec({

    "The truck manager" - {
        "when communicating with Azure Digital Twins" - {
            "should fetch a model" {
                val model = this::class.java.classLoader
                    .getResource(MODEL)?.readText()
                    ?.replace("\n", "")
                    ?.replace(" ", "")

                manager.getTruckDigitalTwinModel() shouldBe model
            }
            "should count correctly" {
                manager.getTruckCount() shouldBeGreaterThan -1
            }
            "should create a digital twin" {
                val truck = Truck(TRUCK_ID)
                manager.createTruck(truck)
                manager.getTruck(truck.truckId) shouldBe truck
            }
            "should update a digital twin" {
                val truck = manager.getTruck(TRUCK_ID)
                truck.position shouldBe Truck(TRUCK_ID).position
                truck.occupiedVolume shouldBe Truck(TRUCK_ID).occupiedVolume
                truck.isInMission shouldBe Truck(TRUCK_ID).isInMission

                manager.updateTruckPosition(TRUCK_ID, NEW_POSITION)
                manager.updateTruckOccupiedVolume(TRUCK_ID, NEW_VOLUME)
                manager.updateTruckInMission(TRUCK_ID, NEW_MISSION)

                val newTruck = manager.getTruck(TRUCK_ID)
                newTruck.position shouldBe NEW_POSITION
                newTruck.occupiedVolume shouldBe NEW_VOLUME
                newTruck.isInMission shouldBe NEW_MISSION
            }
            "should delete a digital twin" {
                manager.deleteTruck(TRUCK_ID)
                val e = shouldThrow<ErrorResponseException> {
                    manager.getTruck(TRUCK_ID)
                }
                e.response.statusCode shouldBe ERROR_STATUS_CODE
            }
        }
    }
})
