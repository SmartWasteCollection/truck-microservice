package swc.microservice.truck.usecases.queries

import swc.microservice.truck.entities.Truck
import swc.microservice.truck.usecases.TruckManager

class GetAvailableTrucks : TruckQuery<List<Truck>> {
    override fun execute(manager: TruckManager): List<Truck> = manager.getAvailableTrucks()
}