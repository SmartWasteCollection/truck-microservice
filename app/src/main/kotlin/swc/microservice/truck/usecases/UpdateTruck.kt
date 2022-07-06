package swc.microservice.truck.usecases

import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.events.AvailabilityUpdateEvent
import swc.microservice.truck.entities.events.OccupiedVolumeUpdateEvent
import swc.microservice.truck.entities.events.PositionUpdateEvent
import swc.microservice.truck.entities.events.TruckUpdateEvent

class UpdateTruck<T>(private val truckId: String, private val event: TruckUpdateEvent<T>) : TruckUseCase<Truck?> {
    override fun execute(manager: TruckManager): Truck? = when (event) {
        is PositionUpdateEvent -> manager.updateTruckPosition(truckId, event.newValue)
        is OccupiedVolumeUpdateEvent -> manager.updateTruckOccupiedVolume(truckId, event.newValue)
        is AvailabilityUpdateEvent -> manager.updateTruckInMission(truckId, event.newValue)
        else -> null
    }
}
