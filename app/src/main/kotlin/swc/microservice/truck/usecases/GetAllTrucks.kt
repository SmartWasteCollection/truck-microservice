package swc.microservice.truck.usecases

import swc.microservice.truck.entities.Truck

class GetAllTrucks : TruckUseCase<List<Truck>> {
    override fun execute(manager: TruckManager): List<Truck> = manager.getAllTrucks()
}
