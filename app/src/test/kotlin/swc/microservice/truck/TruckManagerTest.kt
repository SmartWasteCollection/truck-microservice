package swc.microservice.truck

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import swc.microservice.truck.Values.MODEL
import swc.microservice.truck.usecases.TruckDigitalTwinManager
import java.io.File
import java.nio.file.Files

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

                TruckDigitalTwinManager.getTruckDigitalTwinModel() shouldBe model
            }
        }
    }
})
