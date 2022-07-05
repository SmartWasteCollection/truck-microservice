package swc.microservice.truck.drivers

import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import swc.microservice.truck.drivers.Values.ERROR_STATUS_CODE
import swc.microservice.truck.drivers.Values.MODEL
import swc.microservice.truck.drivers.Values.NEW_MISSION
import swc.microservice.truck.drivers.Values.NEW_POSITION
import swc.microservice.truck.drivers.Values.NEW_VOLUME
import swc.microservice.truck.drivers.Values.manager
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume

object Values {
    const val MODEL = "Truck.json"
    const val ERROR_STATUS_CODE = 404

    val NEW_POSITION = Position(100L, 100L)
    val NEW_VOLUME = Volume(100.0)
    const val NEW_MISSION = true

    val manager: TruckDigitalTwinManager = TruckDigitalTwinManager()
}

class TruckManagerTest : FreeSpec({
    val id = "TruckTest${System.currentTimeMillis()}"

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
                val truck = Truck(id)
                manager.createTruck(truck)
                manager.getTruck(truck.truckId) shouldBe truck
            }
            "should update a digital twin" {
                val truck = manager.getTruck(id)
                truck.position shouldBe Truck(id).position
                truck.occupiedVolume shouldBe Truck(id).occupiedVolume
                truck.isInMission shouldBe Truck(id).isInMission

                manager.updateTruckPosition(id, NEW_POSITION)
                manager.updateTruckOccupiedVolume(id, NEW_VOLUME)
                manager.updateTruckInMission(id, NEW_MISSION)

                val newTruck = manager.getTruck(id)
                newTruck.position shouldBe NEW_POSITION
                newTruck.occupiedVolume shouldBe NEW_VOLUME
                newTruck.isInMission shouldBe NEW_MISSION
            }
            "should read digital twins" {
                waitSomeTime()
                manager.getAllTrucks().size shouldBeGreaterThan 0
            }
            "should delete a digital twin" {
                manager.deleteTruck(id)
                val e = shouldThrow<ErrorResponseException> {
                    manager.getTruck(id)
                }
                e.response.statusCode shouldBe ERROR_STATUS_CODE
            }
        }
    }
})

fun waitSomeTime() {
    Thread.sleep(1_000)
}
