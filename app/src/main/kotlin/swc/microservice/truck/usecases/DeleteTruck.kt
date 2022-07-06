package swc.microservice.truck.usecases

import swc.microservice.truck.entities.Truck

class DeleteTruck(private val truckId: String) : TruckUseCase<Truck?> {
    override fun execute(manager: TruckManager): Truck? = manager.deleteTruck(truckId)
}
