package swc.microservice.truck

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import swc.microservice.truck.Values.MODEL
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.usecases.TruckManager

object Values {
    const val MODEL = "Truck.json"
}

class TruckManagerTest : FreeSpec({

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
            "should create and read a digital twin" {
                val truck = Truck("Truck000")
                TruckManager.createTruckDigitalTwin(truck)
                TruckManager.readTruckDigitalTwin(truck.truckId) shouldBe truck
            }
        }
    }
})
