package swc.microservice.truck.usecases

import swc.microservice.truck.entities.Truck

class CreateTruck(private val truck: Truck) : TruckUseCase<Unit> {
    override fun execute(manager: TruckManager): Unit = manager.createTruck(truck)
}
