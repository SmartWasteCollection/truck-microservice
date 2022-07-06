package swc.microservice.truck.drivers

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import swc.microservice.truck.drivers.Values.MODEL
import swc.microservice.truck.drivers.Values.NEW_MISSION
import swc.microservice.truck.drivers.Values.NEW_POSITION
import swc.microservice.truck.drivers.Values.NEW_VOLUME
import swc.microservice.truck.drivers.Values.manager
import swc.microservice.truck.drivers.digitaltwins.TruckDigitalTwinManager
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume

object Values {
    const val MODEL = "Truck.json"

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
                truck shouldNotBe null
                truck?.position shouldBe Truck(id).position
                truck?.occupiedVolume shouldBe Truck(id).occupiedVolume
                truck?.isInMission shouldBe Truck(id).isInMission

                manager.updateTruckPosition(id, NEW_POSITION)
                manager.updateTruckOccupiedVolume(id, NEW_VOLUME)
                manager.updateTruckInMission(id, NEW_MISSION)

                val newTruck = manager.getTruck(id)
                newTruck shouldNotBe null
                newTruck?.position shouldBe NEW_POSITION
                newTruck?.occupiedVolume shouldBe NEW_VOLUME
                newTruck?.isInMission shouldBe NEW_MISSION
            }
            "should read digital twins" {
                shouldNotThrow<Exception> {
                    manager.getAllTrucks()
                }
            }
            "should delete a digital twin" {
                manager.deleteTruck(id)
                manager.getTruck(id) shouldBe null
            }
        }
    }
})
