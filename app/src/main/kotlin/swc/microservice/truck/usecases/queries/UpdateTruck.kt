package swc.microservice.truck.usecases.queries

import swc.microservice.truck.entities.Position
import swc.microservice.truck.usecases.TruckManager

class UpdateTruck<T>(private val truckId: String, private val newValue: T) : TruckQuery<Unit> {
    override fun execute(manager: TruckManager) {
        manager.updateTruck()
    }
}