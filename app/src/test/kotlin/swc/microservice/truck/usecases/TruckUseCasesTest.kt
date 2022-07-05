package swc.microservice.truck

import io.kotest.core.spec.style.FreeSpec
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume
import swc.microservice.truck.usecases.TruckManager

class TruckUseCasesTest : FreeSpec({

    val trucks: List<Truck> = listOf()
    val manager: TruckManager = object : TruckManager {
        override fun getTruckCount(): Int = trucks.size

        override fun createTruck(truck: Truck): String {
            TODO("Not yet implemented")
        }

        override fun getTruck(id: String): Truck {
            TODO("Not yet implemented")
        }

        override fun deleteTruck(id: String) {
            TODO("Not yet implemented")
        }

        override fun updateTruckPosition(id: String, position: Position) {
            TODO("Not yet implemented")
        }

        override fun updateTruckOccupiedVolume(id: String, volume: Volume) {
            TODO("Not yet implemented")
        }

        override fun updateTruckInMission(id: String, inMission: Boolean) {
            TODO("Not yet implemented")
        }

        override fun getAllTrucks(): List<Truck> = trucks
    }

    "When interacting with the domain" - {
        "the use cases should" - {
            "create a truck" {

            }
        }
    }
})
