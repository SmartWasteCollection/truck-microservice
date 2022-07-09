package swc.microservice.truck.usecases

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume
import swc.microservice.truck.entities.events.AvailabilityUpdateEvent
import swc.microservice.truck.entities.events.OccupiedVolumeUpdateEvent
import swc.microservice.truck.entities.events.PositionUpdateEvent
import swc.microservice.truck.usecases.Values.id1
import swc.microservice.truck.usecases.Values.id2
import swc.microservice.truck.usecases.Values.id3

object Values {
    const val id1 = "Truck0"
    const val id2 = "Truck1"
    const val id3 = "Truck2"
}

class TruckUseCasesTest : FreeSpec({

    var trucks: List<Truck> = listOf()
    val manager: TruckManager = object : TruckManager {
        override fun createTruck(truck: Truck): String {
            trucks = trucks + truck
            return truck.truckId
        }

        override fun getTruck(id: String): Truck? = trucks.find { it.truckId == id }

        override fun deleteTruck(id: String): Truck? {
            val deleted = getTruck(id)
            trucks = trucks.filter { it.truckId != id }
            return deleted
        }

        override fun updateTruckPosition(id: String, position: Position): Truck? {
            trucks = trucks.map {
                when (it.truckId) {
                    id -> Truck(id, position, it.occupiedVolume, it.capacity, it.isInMission)
                    else -> it
                }
            }
            return getTruck(id)
        }

        override fun updateTruckOccupiedVolume(id: String, volume: Volume): Truck? {
            trucks = trucks.map {
                when (it.truckId) {
                    id -> Truck(id, it.position, volume, it.capacity, it.isInMission)
                    else -> it
                }
            }
            return getTruck(id)
        }

        override fun updateTruckInMission(id: String, inMission: Boolean): Truck? {
            trucks = trucks.map {
                when (it.truckId) {
                    id -> Truck(id, it.position, it.occupiedVolume, it.capacity, inMission)
                    else -> it
                }
            }
            return getTruck(id)
        }

        override fun getAllTrucks(): List<Truck> = trucks
    }

    "When interacting with the domain" - {
        "the use cases should" - {
            "create trucks" {
                trucks shouldBe listOf()
                val useCases = listOf(
                    CreateTruck(Truck(id1)),
                    CreateTruck(Truck(id2)),
                    CreateTruck(Truck(id3))
                )
                useCases.forEach { it.execute(manager) }

                GetAllTrucks().execute(manager).size shouldBe 3
            }
            "get trucks" {
                GetTruck(id1).execute(manager) shouldNotBe null
                GetTruck(id2).execute(manager) shouldNotBe null
                GetTruck(id3).execute(manager) shouldNotBe null
            }
            "update a position" {
                val newPosition = Position(1.0, 1.0)
                UpdateTruck(id1, PositionUpdateEvent(newPosition)).execute(manager)
                GetTruck(id1).execute(manager)?.position shouldBe newPosition
            }
            "update a occupied volume" {
                val newOccupiedVolume = Volume(50.0)
                UpdateTruck(id2, OccupiedVolumeUpdateEvent(newOccupiedVolume)).execute(manager)
                GetTruck(id2).execute(manager)?.occupiedVolume shouldBe newOccupiedVolume
            }
            "update an availability" {
                val newMission = true
                UpdateTruck(id3, AvailabilityUpdateEvent(newMission)).execute(manager)
                GetAvailableTrucks().execute(manager).size shouldBe 2
                GetInMissionTrucks().execute(manager).size shouldBe 1
            }
            "delete a truck" {
                val truckCount = GetAllTrucks().execute(manager).size
                DeleteTruck(id1).execute(manager)
                GetAllTrucks().execute(manager).size shouldBe (truckCount - 1)
            }
        }
    }
})
