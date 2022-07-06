package swc.microservice.truck.usecases

import swc.microservice.truck.entities.Position
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.Volume

interface TruckManager {

    fun getTruckCount(): Int

    fun createTruck(truck: Truck): String

    fun getTruck(id: String): Truck?

    fun deleteTruck(id: String): Truck?

    fun updateTruckPosition(id: String, position: Position): Truck?

    fun updateTruckOccupiedVolume(id: String, volume: Volume): Truck?

    fun updateTruckInMission(id: String, inMission: Boolean): Truck?

    fun getAllTrucks(): List<Truck>
}
