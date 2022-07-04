package swc.microservice.truck

import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import swc.microservice.truck.Values.ERROR_STATUS_CODE
import swc.microservice.truck.Values.MODEL
import swc.microservice.truck.Values.TRUCK_ID
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.usecases.TruckManager

object Values {
    const val MODEL = "Truck.json"
    const val TRUCK_ID = "TruckTest"
    const val ERROR_STATUS_CODE = 404
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
                val truck = Truck(TRUCK_ID)
                TruckManager.createTruckDigitalTwin(truck)
                TruckManager.readTruckDigitalTwin(truck.truckId) shouldBe truck
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
