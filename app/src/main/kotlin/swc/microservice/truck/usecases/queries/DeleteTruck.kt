package swc.microservice.truck.usecases.queries

import swc.microservice.truck.usecases.TruckManager

class DeleteTruck(private val truckId: String) : TruckQuery<Unit> {
    override fun execute(manager: TruckManager) {
        manager.deleteTruck(truckId)
    }
}
