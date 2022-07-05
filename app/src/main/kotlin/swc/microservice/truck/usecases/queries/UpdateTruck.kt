package swc.microservice.truck.usecases.queries

import swc.microservice.truck.entities.events.AvailabilityUpdateEvent
import swc.microservice.truck.entities.events.OccupiedVolumeUpdateEvent
import swc.microservice.truck.entities.events.PositionUpdateEvent
import swc.microservice.truck.entities.events.TruckUpdateEvent
import swc.microservice.truck.usecases.TruckManager

class UpdateTruck<T>(private val truckId: String, private val event: TruckUpdateEvent<T>) : TruckQuery<Unit> {
    override fun execute(manager: TruckManager) {
        when (event) {
            is PositionUpdateEvent -> manager.updateTruckPosition(truckId, event.newValue)
            is OccupiedVolumeUpdateEvent -> manager.updateTruckOccupiedVolume(truckId, event.newValue)
            is AvailabilityUpdateEvent -> manager.updateTruckInMission(truckId, event.newValue)
        }
    }
}