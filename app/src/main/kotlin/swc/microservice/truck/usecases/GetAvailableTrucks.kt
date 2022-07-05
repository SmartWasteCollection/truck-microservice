package swc.microservice.truck.usecases

import swc.microservice.truck.entities.Truck

class GetAvailableTrucks : TruckUseCase<List<Truck>> {
    override fun execute(manager: TruckManager): List<Truck> = manager.getAllTrucks().filter { !it.isInMission }
}
