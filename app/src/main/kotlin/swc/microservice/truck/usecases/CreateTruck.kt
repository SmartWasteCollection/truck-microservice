package swc.microservice.truck.usecases

import swc.microservice.truck.entities.Truck

class CreateTruck(private val truck: Truck) : TruckUseCase<String> {
    override fun execute(manager: TruckManager): String = manager.createTruck(truck)
}
