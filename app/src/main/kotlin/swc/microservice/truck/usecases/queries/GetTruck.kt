package swc.microservice.truck.usecases.queries

import swc.microservice.truck.entities.Truck
import swc.microservice.truck.usecases.TruckManager

class GetTruck(private val truckId: String): TruckQuery<Truck> {
    override fun execute(manager: TruckManager): Truck = manager.getTruck(truckId)
}