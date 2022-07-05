package swc.microservice.truck.usecases.queries

import swc.microservice.truck.entities.Truck
import swc.microservice.truck.usecases.TruckManager

class CreateTruck(private val truck: Truck) : TruckQuery<String> {
    override fun execute(manager: TruckManager): String = manager.createTruck(truck)
}
