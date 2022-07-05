package swc.microservice.truck.usecases

import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume

interface TruckManager {

    fun getTruckCount(): Int

    fun getTruckNextId(): String

    fun createTruck(truck: Truck): String

    fun getTruck(id: String): Truck

    fun deleteTruck(id: String)

    fun updateTruckPosition(id: String, position: Position)

    fun updateTruckOccupiedVolume(id: String, volume: Volume)

    fun updateTruckInMission(id: String, inMission: Boolean)

    fun getAvailableTrucks(): List<Truck>
}
